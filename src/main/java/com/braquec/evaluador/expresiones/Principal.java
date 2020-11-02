/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.braquec.evaluador.expresiones;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author braquec
 */
public class Principal {
    private static String textoExp = "";
    private static String opcionMenu = "";
    private static Stack<Character> pila = new Stack<Character>();
    
    public static void main(String[] args){
        System.out.println("¡Bienvenido!");
        Scanner leer = new Scanner(System.in);
        System.out.println("Ingrese la expresión a evaluar:");
        textoExp = leer.nextLine();
        do {
            System.out.println("******************************************************");
            System.out.println("La expresion que ha ingresado es: " + textoExp);
            System.out.println("Ingrese la opcion que desea realizar:");
            System.out.println("1. Convertir expresion a PosFija.");
            System.out.println("2. Evaluar expresión.");
            System.out.println("3. Cambiar expresión ingresada.");
            System.out.println("4. Salir de la aplicacion");
            opcionMenu = leer.nextLine();
            switch (opcionMenu) {
                case "1":
                    //Validamos que los parentesis esten correctamente anidados
                    if(!validarParentesis(textoExp)){
                        System.out.println("Problema los parentesis no estan correctamente anidados!!!");
                        break;
                    }
                    //Validamos que la emprecion no inicie con un operador
                    if(empiezaConOperador(textoExp)){
                        System.out.println("Problema inicia con operador!!!");
                        break;
                    }
                    //Validamos que la emprecion no termine con un operador
                    if(terminaConOperador(textoExp)){
                        System.out.println("La expresion termina con operador!!!");
                        break;
                    }

                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println("Esta es la expresion PostFija:");
                    System.out.println(infijoAPostfijoTxt(textoExp));
                    //System.out.println();
                    break;
                case "2":
                    //Validamos que los parentesis esten correctamente anidados
                    if(!validarParentesis(textoExp)){
                        System.out.println("Problema los parentesis no estan correctamente anidados!!!");
                        break;
                    }
                    //Validamos que la emprecion no inicie con un operador
                    if(empiezaConOperador(textoExp)){
                        System.out.println("Problema inicia con operador!!!");
                        break;
                    }
                    //Validamos que la emprecion no termine con un operador
                    if(terminaConOperador(textoExp)){
                        System.out.println("La expresion termina con operador!!!");
                        break;
                    }

                    for (int i = 0; i < 50; ++i) System.out.println();
                    int operacion = evaluarPosfijo(toPosfijo(textoExp));
                    System.out.println("**********************************");
                    System.out.println("Resultado de evaluar la expresion: ");
                    System.out.println(String.valueOf(operacion));
                    
                    break;
                case "3":
                    System.out.println("Ingrese la expresión a evaluar:");
                    textoExp = leer.nextLine();
                    break;
                case "4":
                    break;
                default:
                    throw new AssertionError();
            }
        } while(!"4".equals(opcionMenu));
   }
   
    public static String infijoAPostfijoTxt(String infijo){
        Pila resultadoPila = infijoAPostfijo(infijo);
        String textResultado = "";
        while(resultadoPila.i > 0)
            textResultado = resultadoPila.pop() + textResultado;
        return textResultado;
        
    }
    
    public static Pila infijoAPostfijo(String infijo){
        infijo +=')'; // Agregamos al final del infijo un ")"
		int tamaño = infijo.length();
		Pila PilaDefinitiva = new Pila(tamaño);
		Pila PilaTemp = new Pila(tamaño);
		PilaTemp.push('('); // Agregamos a la pila temporal un "(" />
		for (int i = 0; i < tamaño; i++) {
			char caracter = infijo.charAt(i);
			switch (caracter) {
			case '(':
				PilaTemp.push(caracter);
				break;
			case '+':case '-':case '^':case '*':case '/':
				while (jerarquia(caracter) <= jerarquia(PilaTemp.nextPop()))
					PilaDefinitiva.push(PilaTemp.pop());
				PilaTemp.push(caracter);
				break;
			case ')':
				while (PilaTemp.nextPop() != '(')
                                    PilaDefinitiva.push(PilaTemp.pop());
				PilaTemp.pop();
				break;
			default:
                                //System.out.println(caracter);
				PilaDefinitiva.push(caracter);
			}
		}
		return PilaDefinitiva;
    }
    
    public static int jerarquia(char elemento){
        int res = 0;
        switch (elemento) {
        case ')':
                res = 5; break;
        case '^':
                res = 4; break;
        case '*': case '/':
                res = 3; break;
        case '+': case '-':
                res = 2; break;
        case '(':
                res = 1; break;
        }
        return res;
    }
    
    public static boolean validarParentesis(String operacion){
        Stack<Character> pila = new Stack<Character>();
        
        char[] ecuacion = operacion.toCharArray();        
        for(int c=0;c<ecuacion.length;c++){
            char caracter = ecuacion[c];
            if(caracter=='('){
                pila.push(caracter);
            }
            else if(caracter==')'){
                if(pila.empty()){
                    return false;
                }
                else{
                    pila.pop();
                }
            }
        }
        if(!pila.empty()){    
            return false;
        }        
        return true;
    }
    
    public static boolean empiezaConOperador(String infijo){
        char[] cadena = infijo.toCharArray();
        for(int c=0;c<cadena.length;c++){
            char caracter = cadena[c];
            if(Character.isDigit(caracter)){
                return false;
            }
            if(Character.isLetter(caracter)){
                return false;
            }
            else if(caracter=='+' || caracter =='-' || caracter=='*' || caracter=='/' || caracter=='^'){
                return true;
            }
        }
        return false;
    }
    
    public static boolean terminaConOperador(String infijo){
        char[] cadena = infijo.toCharArray();
        for(int c=cadena.length-1;c>0;c--){
            char caracter = cadena[c];
            if(Character.isDigit(caracter)){
                return false;
            }
            if(Character.isLetter(caracter)){
                return false;
            }
            else if(caracter=='+' || caracter =='-' || caracter=='*' || caracter=='/' || caracter=='^'){
                return true;
            }
        }
        return false;
    }
    
    public static String toPosfijo(String infijo){             
        String salida="";
        char[] cadena = infijo.toCharArray();
        String[] abc = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
                "o","p","q","r","s","t","u","v","w","x","y","z"};
                // Convert String Array to List
                List<String> list = Arrays.asList(abc);
                
        for(int c=0;c<cadena.length;c++){
            char caracter = cadena[c];
            String caracterString = Character.toString(caracter);
            if (list.contains(caracterString)) {
                System.out.println("Ingrese el valor para la variable: "+ caracter);
                Scanner leer = new Scanner(System.in);
                caracter = leer.next(".").charAt(0);
            }
            if(caracter=='('){
                pila.push(caracter);
            } 
            else if(caracter==')'){                
                while(true){
                    if(pila.empty()){
                        System.out.println("Operacion invalida numero de parentesis impares");
                        return null;
                    }
                    char temp = pila.pop().charValue();
                    if(temp=='('){
                        break;
                    }
                    else{
                        salida+=" "+temp;
                    }
                }//fin del wile
            }
            else if(Character.isDigit(caracter)){
                salida+=" "+caracter;                
                c++;
                busqueda : for( ; c<cadena.length;c++){
                    if(Character.isDigit(cadena[c])){
                        salida+=cadena[c];
                    }
                    else{
                        c--;
                        break busqueda;
                    }                    
                }                
            }
            else if(caracter=='+'||caracter=='-'||caracter=='/'||caracter=='*'||caracter=='^'){   
                if(pila.empty()){
                    pila.push(caracter);
                }
                else{
                    while(true){                    
                        if(esDeMayorPresedencia(caracter)){                            
                            pila.push(caracter);
                            break;
                        }else{
                            salida+=" "+pila.pop();
                        }  
                    }                                 
                }               
            }
            else{
                System.out.println("caracter no valido para la exprecion :"+caracter);                
                return null;
            }
        }//fin del for
        if(!pila.empty()){
            do{
                char temp = pila.pop().charValue();
                salida+=" "+temp;                
            }while(!pila.empty());
        }
        
        return salida.trim();
    }
    
    private static boolean esDeMayorPresedencia(char caracter){
        if(pila.empty()){
            return true;
        }
        if(caracter==pila.peek().charValue()){
            return false;
        }
        if(caracter=='^'){
            return true;
        }
        if( (caracter=='*'&&pila.peek().charValue()=='/')||(caracter=='/'&&pila.peek().charValue()=='*')){
            return false;
        }
        if( (caracter=='+'&&pila.peek().charValue()=='-')||(caracter=='-'&&pila.peek().charValue()=='+')){
            return false;
        }        
        else if(caracter=='-'||caracter=='+'){
            char temp = pila.peek().charValue();
            if(temp=='*'||temp=='/'){
                return false;
            }
        }
        return true;        
    }
    
    public static int evaluarPosfijo(String posfijo){              
        ArrayList<String> token = new ArrayList<String>();
        
        
        StringTokenizer st = new StringTokenizer(posfijo," ");
        while(st.hasMoreTokens()){
            token.add(st.nextToken());
        }
        
        if(token.size()==1){
            return Integer.parseInt(token.get(0));                
        }
        int c=0;
        //areaTexto.append(token.toString()+"\n");
        while(token.size()!=1){
            
            String operador = token.get(c);
            if(operador.equals("+")||operador.equals("-")||operador.equals("*")||operador.equals("/")||operador.equals("^")){
                String operando1=token.get(c-1);
                String operando2 =token.get(c-2);
                
                token.remove(c);
                token.remove(c-1);
                token.remove(c-2);
                if(operador.equals("+")){
                    try {
                        String suma = (Integer.parseInt(operando2)+Integer.parseInt(operando1))+"";
                        token.add(c-2,suma);
                        c=0;
                    } catch (Exception e) {
                        //areaTexto.setText("Error al comvertir un operando\n"+e);
                        System.out.println("Error al comvertir un operando: "+e);
                        return 0;
                    }                    
                }
                else if(operador.equals("-")){
                    try {
                        String resta = (Integer.parseInt(operando2)-Integer.parseInt(operando1))+"";
                        token.add(c-2,resta);
                        c=0;
                    } catch (Exception e) {
                        //areaTexto.setText("Error al comvertir un operando\n"+e);
                        System.out.println("Error al comvertir un operando: "+e);
                        return 0;
                    }    
                }
                else if(operador.equals("*")){
                    try {
                        String multiplicacion = (Integer.parseInt(operando2)*Integer.parseInt(operando1))+"";
                        token.add(c-2,multiplicacion);
                        c=0;
                    } catch (Exception e) {
                        //areaTexto.setText("Error al comvertir un operando\n"+e);
                        System.out.println("Error al comvertir un operando: "+e);
                        return 0;
                    }    
                }
                else if(operador.equals("/")){
                    try {
                        String divicion = (Integer.parseInt(operando2)/Integer.parseInt(operando1))+"";
                        token.add(c-2,divicion);
                        c=0;
                    } catch (Exception e) {
                        //areaTexto.setText("Error al comvertir un operando\n"+e);
                        System.out.println("Error al comvertir un operando: "+e);
                        return 0;
                    }    
                }
                else{
                    try {
                        String potencia = (Integer.parseInt(operando2)^Integer.parseInt(operando1))+"";
                        token.add(c-2,potencia);
                        c=0;
                    } catch (Exception e) {
                        //areaTexto.setText("Error al comvertir un operando\n"+e);
                        System.out.println("Error al comvertir un operando: "+e);
                        return 0;
                    }   
                }
                //areaTexto.append(token.toString()+"\n");
                
            }
            else{
                c++;
            }
        }
        
        
        try {            
            return Integer.parseInt(token.get(0));            
        } catch (Exception e) {
            //areaTexto.setText("Error al parsear el resultado\n"+e);
            System.out.println("Error al parsear el resultado");
            return 0;
        }
        
    }
    
    
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
