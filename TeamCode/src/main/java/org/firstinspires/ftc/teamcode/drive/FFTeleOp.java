//Teleop code for Freight Frenzy 2021-2022

package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="FFTeleOp", group="Mecanum Drive") //formerly MecanumWithSpeedToggle
@Config
public class FFTeleOp extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null; //1 port, control hub
    private DcMotor rightFront = null; //2 port, control hub
    private DcMotor leftBack = null; //0 port, control hub
    private DcMotor rightBack = null; //3 port, control hub
    private DcMotor carouselMotor = null; //0 port control hub
    private DcMotor platformMotor = null;//port 1, expansion hub
    private DcMotor liftMotor = null;//port 0, expansion hub
    private CRServo intake;//intake motor, control hub
    private DigitalChannel touch = null;//port 0, control hub
    private DigitalChannel redLED = null;// port 3, control hub
    private DigitalChannel greenLED = null;// port 2 control hub
    private DigitalChannel magnetSensor = null;//port 7, control hub
    private Servo cappingServo = null;
    private boolean freightPresent;
    private int level = 3;
    private boolean liftWasOn = false;
    private int setPoint = 0;
    private double power = 0.0;
    private double servoPower = 0.0;
    // Configurable variables
    public static int TOP = -1100, UPPER_LEVEL = -724, MIDDLE_LEVEL = -217, LOWER_LEVEL = 248, BOTTOM = 589, STARTING_POS = -60;
    public static boolean parkingMode = false;
    public static double normalPower = 1.0;
    public static double parkingPower = 0.375;
    public static double carouselPower = 0.65;
    public static double servoPosition = 0.34; // Position value of servo
    public static double normalServoPower = 0.005; // Value servo pos is incremented by
    public static double parkingServoPower = 0.001;
    public static double liftPowerUp = -1.0;
    public static double liftPowerDown = 0.75;
    public static double platformPower = .75;
    public static double upperDeadBand = 60;
    public static double lowerDeadBand = 65;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        carouselMotor = hardwareMap.get(DcMotor.class, "carouselMotor");
        platformMotor = hardwareMap.get(DcMotor.class, "platformMotor");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        intake = hardwareMap.get(CRServo.class, "intakeServo");
        cappingServo = hardwareMap.get(Servo.class, "cappingServo");
        touch = hardwareMap.get(DigitalChannel.class, "frontTouch");
        magnetSensor = hardwareMap.get(DigitalChannel.class, "magnetSensor");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Get the LED colors and touch sensor from the hardwaremap
        redLED = hardwareMap.get(DigitalChannel.class, "red");
        greenLED = hardwareMap.get(DigitalChannel.class, "green");
        // change LED mode from input to output
        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }



    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftFrontPower = 0;
        double rightFrontPower = 0;
        double leftBackPower = 0;
        double rightBackPower = 0;
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double strafe = gamepad1.left_stick_x;
        double liftCurrPos = liftMotor.getCurrentPosition();
        telemetry.addData("Lift Encoders", liftCurrPos);

        if (gamepad1.left_bumper) {
            parkingMode = true;
        } else if (gamepad1.right_bumper) {
            parkingMode = false;
        } // Switched to else if structure - Everett
        if (parkingMode) {
            power = parkingPower;
            servoPower = parkingServoPower;
            telemetry.addData("Parking mode", "ON");
        } else {
            power = normalPower;
            servoPower = normalServoPower;
            telemetry.addData("Parking mode", "OFF");
        } // Switched to if else structure - Everett


        if (gamepad1.dpad_up) {
            carouselMotor.setPower(carouselPower);
            telemetry.addData("Carousel power: ", carouselPower);
        } else if (gamepad1.dpad_down) {
            carouselMotor.setPower(-carouselPower);
            telemetry.addData("Carousel power", -carouselPower);
        } else {
            carouselMotor.setPower(0.0); // Stops carousel
            telemetry.addData("Carousel power", "No command");
        } // Switched to if - else if - else structure - Everett
        //platform controls
        if (gamepad2.dpad_left) {
            platformMotor.setPower(platformPower);
            telemetry.addData("Platform power: ", platformPower);
        } else if (gamepad2.dpad_right) {
            platformMotor.setPower(-platformPower);
            telemetry.addData("Platform power: ", platformPower);
        } else {
            platformMotor.setPower(0.0);
            telemetry.addData("Platform power", "No command");
        }// Switched to if - else if - else structure - Everett

        //lift controls; for coarse control hold left bumper and use dpad, for fine control just use dpad as normal
        //NOTE: level remains the same after left bumper is released;
        //when left bumper is pressed it will return to that level.
        if (gamepad2.left_bumper) {
            if (!liftWasOn) {
                if (gamepad2.dpad_up) {
                    // level++ runs during the condition
                    if (level++ > 6) {
                        level = 6;
                    }
                }
                if (gamepad2.dpad_down) {
                    // level-- runs during the condition
                    if (level-- < 1) {
                        level = 1;
                    }
                }
            } // Put liftWasOn in outer if condition

            if (gamepad2.dpad_down || gamepad2.dpad_up) {
                liftWasOn = true;
            } else {
                liftWasOn = false;
            }

            switch (level) {
                case 1:
                    setPoint = BOTTOM;break;
                case 2:
                    setPoint = LOWER_LEVEL;break;
                case 3:
                    setPoint = STARTING_POS;break;
                case 4:
                    setPoint = MIDDLE_LEVEL;break;
                case 5:
                    setPoint = UPPER_LEVEL;break;
                case 6:
                    setPoint = TOP;break;
            }

            if (liftMotor.getCurrentPosition() > (setPoint + upperDeadBand)) {
                liftMotor.setPower(-1.0); // Moves lift Up
            } else if (liftMotor.getCurrentPosition() < (setPoint - lowerDeadBand)) {
                liftMotor.setPower(0.75); // Moves lift down; lower power because it is gravity assisted
            } else {
                liftMotor.setPower(0);
            }
        } else {
            if(gamepad2.dpad_up)
            {
                liftMotor.setPower(liftPowerUp);
                telemetry.addData("Lift power: ", Math.abs(liftPowerUp));
            } else if(gamepad2.dpad_down)
            {

                liftMotor.setPower(liftPowerDown);
                telemetry.addData("Lift power", -liftPowerDown);
            } else
            {
                liftMotor.setPower(0.0);
                telemetry.addData("Lift power", "No command");
            }
        }
        // mag sensor testing
        if (!magnetSensor.getState()) {
            telemetry.addData("Magnet Sensor","On");
            platformMotor.setPower(0);
        } else {
            telemetry.addData("Magnet Sensor","Off");
            platformMotor.setPower(platformPower);
        }


        //intake motor control
        if (gamepad2.a && !freightPresent) {

            intake.setPower(-1.0);

            telemetry.addData("Intaking", "");
        }


        if (gamepad2.b) {
            intake.setPower(1.0);
            telemetry.addData("Delivering", "");
        }

        if ((!gamepad2.a || freightPresent) && !gamepad2.b) {
            intake.setPower(0.0);
        }
        //touch sensor and LED
        if (touch.getState()) {
            //Touch Sensor is not pressed
            greenLED.setState(false);
            redLED.setState(true);
            freightPresent = false;

        } else {
            //Touch Sensor is pressed
            redLED.setState(false);
            greenLED.setState(true);
            freightPresent = true;
        }
        telemetry.addData("Freight Present", freightPresent);
        //Capping Servo Control
        if(gamepad1.a) {
            servoPosition += servoPower;
            if (servoPosition >= 0.72) {
                servoPosition = 0.72;
            }
        }
        if(gamepad1.b) {
            servoPosition -= servoPower;
            if (servoPosition <= 0.0) {
                servoPosition = 0.0;
            }
        }
        telemetry.addData("capping servo position: ", servoPosition);
        cappingServo.setPosition(servoPosition);
            //driving code below
            leftFrontPower = Range.clip(drive + turn + strafe, -power, power);
            leftBackPower = Range.clip(drive + turn - strafe, -power, power);
            rightFrontPower = Range.clip(drive - turn - strafe, -power, power);
            rightBackPower = Range.clip(drive - turn + strafe, -power, power);

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;

            // Send calculated power to wheels
            leftFront.setPower(leftFrontPower);
            leftBack.setPower(leftBackPower);
            rightFront.setPower(rightFrontPower);
            rightBack.setPower(rightBackPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        }

        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop () {
        }

    }