package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp
public class EncoderTest extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor liftMotor = null;
    public void init() {
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void init_loop() {

    }
    @Override
    public void start() {
        runtime.reset();
    }
    public void loop() {
        telemetry.addData("Current Time", runtime.seconds());
        telemetry.addData("Current Position", liftMotor.getCurrentPosition());
        telemetry.update();

    }
}
