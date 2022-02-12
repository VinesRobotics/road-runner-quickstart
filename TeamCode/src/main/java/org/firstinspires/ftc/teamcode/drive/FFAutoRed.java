/*
Copyright 2021 FIRST Tech Challenge Team 11341

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous
@Disabled
public class FFAutoRed extends LinearOpMode {
    private Blinker control_Hub;
    private Blinker expansion_Hub_1;
    private DcMotor carouselMotor;
    private DigitalChannel touch;
    private Gyroscope imu2;
    private Gyroscope imu;
    private DcMotor leftBack;
    private DcMotor leftFront;
   // private CRServo markerServo;
    private DcMotor rightBack;
    private DcMotor rightFront;


    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        expansion_Hub_1 = hardwareMap.get(Blinker.class, "Expansion Hub 1");
        carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        touch = hardwareMap.get(DigitalChannel.class, "frontTouch");
        imu2 = hardwareMap.get(Gyroscope.class, "imu2");
        imu = hardwareMap.get(Gyroscope.class, "imu");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
       // markerServo = hardwareMap.get(CRServo.class, "markerServo");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");

        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower= 0;
        double rightBackPower= 0;
        double carouselMotorPower = 0;
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        //while (opModeIsActive()) {
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery
            leftFront.setDirection(DcMotor.Direction.FORWARD);
            rightFront.setDirection(DcMotor.Direction.REVERSE);
            leftBack.setDirection(DcMotor.Direction.FORWARD);
            rightBack.setDirection(DcMotor.Direction.REVERSE);
            telemetry.addData("Status", "Running");
            telemetry.update();
            //double power = .25;
            double power = .45;
            double turn  =  0.25;
            double strafe = -0.35;
            //double drive = 0.2;
            double drive = .3;
            leftFrontPower    = power;
            leftBackPower = power;
            rightFrontPower   = power;
            rightBackPower = power;
            
            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightFront.setPower(rightFrontPower);
            rightBack.setPower(rightBackPower);
            telemetry.addData("Status", "Sleep");
            telemetry.update();
            sleep(250);
            power = 0;
            leftFrontPower    = power;
            leftBackPower = power;
            rightFrontPower   = power;
            rightBackPower = power;
            
            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightFront.setPower(rightFrontPower);
            rightBack.setPower(rightBackPower);
            carouselMotor.setPower(.25);
            sleep(5000);
            
            //drive back from turntable
            power = 1;
            drive = -.25;
            turn = .3;
            strafe = 0;
            leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
            leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
            rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
            rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightFront.setPower(rightFrontPower);
            rightBack.setPower(rightBackPower);
            sleep(700);
            
            //drive towards storage unit
            power = 1;
            drive = .25;
            turn = 0;
            strafe = 0;
            leftFrontPower    = Range.clip(drive + turn + strafe, -power, power) ;
            leftBackPower = Range.clip(drive + turn - strafe, -power, power) ;
            rightFrontPower   = Range.clip(drive - turn - strafe, -power, power) ;
            rightBackPower = Range.clip(drive - turn + strafe, -power, power) ;
            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightFront.setPower(rightFrontPower);
            rightBack.setPower(rightBackPower);
            sleep(1000);
        //}
    }
}
