package frc.robot.subsystems;

/** 
 * hahahahaha enum class heehahah 
 * 
 * https://www.programiz.com/java-programming/enums
 * https://www.programiz.com/java-programming/enum-constructor
 */
public enum ShotRange
{
    //  Format: kNameOfRange(requiredTy, requiredRPM)
    
    kInitLine(69420, 1250), //  10 foot shot
    kTrench(69420, 1600);   //  18 foot shot

    public double requiredTy, requiredRPM;

    private ShotRange(double reqTy, double reqRPM)
    {
        requiredTy = reqTy;
        requiredRPM = reqRPM;
    }
}
/*
    tbh this ShotRange enum class feels *kind of* stupid to have... it's a fair way to store the constants.
    Also, we should try that regression thing that we came up with on Friday morning before comp day...
        That would let us shoot from any given distance
*/