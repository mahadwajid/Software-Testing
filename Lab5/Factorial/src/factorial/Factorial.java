/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factorial;

/**
 *
 * @author FA21-bse-057
 */
public class Factorial {
 
    public static long calculateFactorial(int n){
        if(n < 0){
            throw new IllegalArgumentException("Factorial is not defined");
        }
        if(n == 0){
            return 1;
        }
        
        long result = 1;
        for(int i=2; i<=n; i++){
            result*=i;
        }
        return result;
    }
    
    public static void main(String[] args){
    try{
        int num = 5;
        long factorial= calculateFactorial(num);
        System.out.println("The Factorial of" + num + "is" + factorial );
    }catch(IllegalArgumentException e){
        System.err.println(e.getMessage());
    }
}
}


