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
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

        // Hardware Config
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.FORWARD);

        // Detection Iteration Config
        detectionNum = 15;

        waitForStart();

        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (100) * 0.5; // Movement on y-axis
        right_stick_x = (0) * 0.5; // Rotation modifier

        theta = (Math.atan2(stick_y, stick_x)) - (Math.PI / 2);
        Px = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta + Math.PI / 4));
        Py = Math.sqrt(Math.pow(stick_x, 2) + Math.pow(stick_y, 2)) * (Math.sin(theta - Math.PI / 4));

        rightFront.setPower(Px - right_stick_x);
        leftFront.setPower(Py + right_stick_x);
        rightRear.setPower(Py - right_stick_x);
        leftRear.setPower(Px + right_stick_x);
        sleep(500); // run action for 0.5 seconds
        rightFront.setPower(0);
        leftFront.setPower(0);
        rightRear.setPower(0);
        leftRear.setPower(0);
        // Movement block end

        while (detectionNum > 0) {
            if ((colorSensor.red() > colorSensor.green()) && (colorSensor.red() > colorSensor.blue())) {
                redCount++;
            } else if ((colorSensor.green() > colorSensor.red()) && (colorSensor.green() > colorSensor.blue())) {
                greenCount++;
            } else if ((colorSensor.blue() > colorSensor.green()) && (colorSensor.blue() > colorSensor.red())) {
                blueCount++;
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

    public void positionOne() {
        // pull away
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (-100) * 0.5; // Movement on y-axis
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
        // go left
        // Movement block start
        stick_x = (-100) * 0.5; // Movement on x-axis
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
        stick_y = (100) * 0.5; // Movement on y-axis
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
    }

    public void positionTwo() {
        // push forward
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (100) * 0.5; // Movement on y-axis
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
    }

    public void positionThree() {
        // pull away
        // Movement block start
        stick_x = (0) * 0.5; // Movement on x-axis
        stick_y = (-100) * 0.5; // Movement on y-axis
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
        stick_x = (100) * 0.5; // Movement on x-axis
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
        stick_y = (100) * 0.5; // Movement on y-axis
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
    }
}
