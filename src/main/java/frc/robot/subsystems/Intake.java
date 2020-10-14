package frc.robot.subsystems;


public class Intake {
    XboxController controller;
    VictorSPX intakeMotor;
    
    /** Constructs the intake motor along with 
     *  the Xbox Controller
     *
     */
    public Intake(XboxController x) {
        controller = x;
        intakeMotor = new VictorSPX(Constants.INTAKE_MOTOR_ID);
    }

    public void teleOp()    
    {
        //Intake
        if(controller.buttonX())
        intakeMotor.set(1);
        else if(controller .buttonY()) {
                 intakeMotor.set (-1);
        }
        else {
                intakeMotor.set (0);
            //#endregion
        }
        


    }




}