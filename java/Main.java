import java.util.*;

public class Main
{
	public static void main(String[] args) {
	    Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
      
        ArrayList<String> teste = new ArrayList<>();
        boolean percorrer = true;
        while (percorrer){
            double valor = 0;
            for(int i = 0; i <= String.valueOf(n).length() - 1; i++){
                String numero = String.valueOf(n); //.charAt(i);
                
                numero = String.valueOf(numero.charAt(i));

                
                double digito = Double.parseDouble(numero);
                // System.out.println(digito);
                double quadrado = Math.pow(digito, 2);
                System.out.println(quadrado);
                if (i== 0){
                    valor= quadrado;
                }else{
                    valor += quadrado;
                }
            }
            System.out.println("total "+valor);
            
            if (valor == 1 || valor == 100){
            System.out.println("true");
            percorrer = false;
            }else{
            System.out.println("false");
             n = (int) valor;
            }
        }
    
        }
}