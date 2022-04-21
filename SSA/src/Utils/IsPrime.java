package Utils;

public class IsPrime {
    /**
     * Found on https://www.edureka.co/blog/prime-number-program-in-java/#:~:text=The%20isPrime(int%20n)%20method,%3D%201)%20it%20returns%20false.
     * @param inputNumber
     * @return
     */
    public static boolean checkForPrime(int inputNumber)
    {
        boolean isItPrime = true;

        if(inputNumber <= 1)
        {
            isItPrime = false;

            return isItPrime;
        }
        else
        {
            for (int i = 2; i<= inputNumber/2; i++)
            {
                if ((inputNumber % i) == 0)
                {
                    isItPrime = false;

                    break;
                }
            }

            return isItPrime;
        }
    }
}
