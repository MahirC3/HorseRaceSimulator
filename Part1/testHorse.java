public class testHorse {
    
    public static void main(String[] args) {

        //Create a new horse insstance.
        Horse newHorse = new Horse('♘', "Stallion", 0.7 );

        // Testing the accessor methods of of the Horse.java file, the three methods should simply return.
        System.out.println("Horse Name: " + newHorse.getName());
        System.out.println("Horse Symbol: " + newHorse.getSymbol());
        System.out.println("Horse Confidence: " + newHorse.getConfidence());

        //We know the current symbol is a ♘, we want to change it to S
        newHorse.setSymbol('S');
        //Should output S if working correctly.
        System.out.println("Horse Symbol: " + newHorse.getSymbol());




        //We know that the current horse confidence is 0.7, this method call should set it to 0.3
        newHorse.setConfidence(0.3);
        //Expected output of 0.3 if methods are working correctly.
        System.out.println("Horse Confidence: " + newHorse.getConfidence());


        // We check the distance before we make the horse step
        System.out.println("Distance before step: " + newHorse.getDistanceTravelled());
        //We make the horse step
        newHorse.moveForward();
        //Check the distance after we step, if the distance is 1, our methods are working correctly.
        System.out.println("Distance after step: " + newHorse.getDistanceTravelled());

        //Check if the horse is currently fallen, should return false.
        System.out.println("Has fallen?" + newHorse.hasFallen());
        //Call the fall method.
        newHorse.fall();
        //Check if the horse has now fallen, should return true if method is working correctly.
        System.out.println("Has fallen?" + newHorse.hasFallen());


        System.out.println("\nBack to start\n");
        //This should reset distance back to 0, and fall back to false.
        newHorse.goBackToStart();
        System.out.println("Has fallen?" + newHorse.hasFallen());
        System.out.println("Distance after step: " + newHorse.getDistanceTravelled());


    }
}
