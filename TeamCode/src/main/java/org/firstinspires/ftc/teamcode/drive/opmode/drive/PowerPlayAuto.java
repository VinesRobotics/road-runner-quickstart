package org.firstinspires.ftc.teamcode.drive.opmode.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "0CurrentAutos")
public class PowerPlayAuto extends LinearOpMode {
    private DcMotor rightFront = null; //0 port, control hub
    private DcMotor leftFront = null; //1 port, control hub
    private DcMotor rightRear = null; //2 port, control hub
    private DcMotor leftRear = null; //3 port, control hub
    private ColorSensor colorSensor = null;
    private double theta;
    private double stick_y, stick_x, Px, Py, right_stick_x;
    private int detectionNum, redCount, greenCount, blueCount;



    @Override
    public void runOpMode() throws InterruptedException {
        // HardwareMap Definitions
        rightFront = hardwareMap.get(DcMotorEx.class, "leftFront"); // motor hardwaremap differs due to different front in relation due to autonomous
        leftFront = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        // Hardware Config
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);

        // Detection Iteration Config
        detectionNum = 15;

        waitForStart();

        // Movement block start - forward
        right_stick_x = (0) * 0.5; // Rotation modifier
        /*
        Px = 0.40;
        Py = 0.40;

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(1150); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(500);

        // left
        right_stick_x = (0) * 0.5; // Rotation modifier
        Px = 0.40;
        Py = -0.40;

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(1150); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(500);

        //right
        right_stick_x = (0) * 0.5; // Rotation modifier
        Px = -0.40;
        Py = 0.40;

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(1150); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(500);

        // back
        right_stick_x = (0) * 0.5; // Rotation modifier
        Px = -0.40;
        Py = -0.40;

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(1150); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(500);
        // Movement block end
      */
        moveRobot(0.40,0.40,0,750,500);
        while (detectionNum > 0) {
            if ((colorSensor.red() > colorSensor.green()) && (colorSensor.red() > colorSensor.blue())) {
                redCount++;
                detectionNum--;
            } else if ((colorSensor.green() > colorSensor.red()) && (colorSensor.green() > colorSensor.blue())) {
                greenCount++;
                detectionNum--;
            } else if ((colorSensor.blue() > colorSensor.green()) && (colorSensor.blue() > colorSensor.red())) {
                blueCount++;
                detectionNum--;
            }
        }
        if ((redCount > greenCount) && (redCount > blueCount)) {
            positionOne();
        } else if ((greenCount > redCount) && (greenCount > blueCount)) {
            positionTwo();
        } else {
            positionThree();
        }

    }
    private void moveRobot(double powerX, double powerY, double rotateX, long moveTime, long waitTime)
    {
        rightFront.setPower(powerX - rotateX);
        leftFront.setPower(powerY + rotateX);
        rightRear.setPower(powerY - rotateX);
        leftRear.setPower(powerX + rotateX);
        sleep(moveTime);
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(waitTime);
    }
    public void positionOne() {

        // Back
        moveRobot(-0.40,-0.40,0,800, 500);
        // Left
        moveRobot(0.40,-0.40,0,800,500);
        // Forward
        moveRobot(0.40,0.40,0,1500,500);
        /*
        // Movement block start
      //  stick_x = (1) * 0.5; // Movement on x-axis
        //stick_y = (0) * 0.5; // Movement on y-axis
      //  right_stick_x = (0) * 0.5; // Rotation modifier

     //   theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
    //    Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
      //  Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

       /* Px = -0.40
        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(250); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(100);
        // Movement block end
        // pull away
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (-1.0) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(400); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        sleep(500);

        */
        // Movement block end
//        // go left
//        // Movement block start
//        stick_x = (1) * 0.5; // Movement on x-axis
//        stick_y = (0) * 0.5; // Movement on y-axis
//        right_stick_x = (0) * 0.5; // Rotation modifier
//
//        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
//        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
//        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));
//
//        rightFront.setPower(Px - right_stick_x);
//        leftFront.setPower(Py + right_stick_x);
//        rightRear.setPower(Py - right_stick_x);
//        leftRear.setPower(Px + right_stick_x);
//        sleep(1000); // run action for 0.5 seconds
//        rightFront.setPower(0);
//        leftFront.setPower(0);
//        rightRear.setPower(0);
//        leftRear.setPower(0);
//        sleep(500);
//        // Movement block end
//        // go forward
//        // Movement block start
//        stick_x = (0) * 0.5; // Movement on x-axis
//        stick_y = (1.0) * 0.5; // Movement on y-axis
//        right_stick_x = (0) * 0.5; // Rotation modifier
//
//        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
//        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
//        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));
//
//        rightFront.setPower(Px - right_stick_x);
//        leftFront.setPower(Py + right_stick_x);
//        rightRear.setPower(Py - right_stick_x);
//        leftRear.setPower(Px + right_stick_x);
//        sleep(750); // run action for 0.5 seconds
//        rightFront.setPower(0);
//        leftFront.setPower(0);
//        rightRear.setPower(0);
//        leftRear.setPower(0);
//        sleep(500);
//    // Movement block end
    }

    public void positionTwo() {
        // Forward
        moveRobot(0.40,0.40,0,500,500);
        /*
        // push forward
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (1.0) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(250); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        // Movement block end

         */
    }

    public void positionThree() {
        // Back
        moveRobot(-0.40,-0.40,0,800,500);
        // Right
        moveRobot(-0.40,0.40,0,800,500);
        // Forward
        moveRobot(0.40,0.40,0,1500,500);
        /*
        // pull away
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (-1.0) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(400); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        // Movement block end

        // go right
        // Movement block start
        stick_x = (1.0) * 0.5; // Movement on x-axis
        stick_y = (0) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(400); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        // Movement block end

        // go forward
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (1.0) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(750); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        // Movement block end
        /*
         */
    }
}
