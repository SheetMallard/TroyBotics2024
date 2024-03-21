package frc.robot.Commands;

import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class TurnCommand {
    private Timer timer = new Timer();
    private Talon m_right, m_left;
    public TurnCommand(Talon mRight, Talon mLeft){
        m_right = mRight;
        m_left = mLeft;
    }
    public void quarter(){
        timer.start();
        if (timer.get() < 2){
            m_left.set(0.5);
            m_right.set(0.5);
        }
        else{
            timer.stop();
            timer.reset();
        }
    }
}
