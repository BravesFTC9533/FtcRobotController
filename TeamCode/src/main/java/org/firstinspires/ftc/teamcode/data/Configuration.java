package org.firstinspires.ftc.teamcode.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Configuration {

    private static final String PREFERENCES = "RobotPref";
    private SharedPreferences sharedPreferences;

    // Starting Configurations
    public static final String ALLIANCE_COLOR = "AllianceColor";
    public static AllianceColor allianceColor;
    public enum AllianceColor {
        RED, BLUE;

        public static AllianceColor toAllianceColor(String allianceColor) {
            try { return valueOf(allianceColor); }
            catch (Exception ignored) { return BLUE; }
        }
    }

    public static final String STARTING_POSITION = "StartingPosition";
    public static StartingPosition startingPosition;
    public enum StartingPosition {
        INNER, OUTER;

        public static StartingPosition toStartingPosition(String startingPosition) {
            try { return valueOf(startingPosition); }
            catch (Exception ignored) { return OUTER; }
        }
    }

    // Lift Configurations
    public static final String MAX_LIFT_TICKS = "MaxLiftTicks";
    public static int maxLiftTicks;

    // Launcher Angle Configuration
    public static final String LAUNCHER_ANGLE_LOW = "LauncherAngleLow";
    public static int launcherAngleLow;

    public static final String LAUNCHER_ANGLE_MID = "LauncherAngleMid";
    public static int launcherAngleMid;

    public static final String LAUNCHER_ANGLE_HIGH = "LauncherAngleHigh";
    public static int launcherAngleHigh;

    public Configuration(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        this.load();
    }

    public void load() {
        allianceColor = AllianceColor.toAllianceColor(sharedPreferences.getString(ALLIANCE_COLOR, "BLUE"));
        startingPosition = StartingPosition.toStartingPosition(sharedPreferences.getString(STARTING_POSITION, "OUTER"));
        maxLiftTicks = sharedPreferences.getInt(MAX_LIFT_TICKS, 1000);
        launcherAngleLow = sharedPreferences.getInt(LAUNCHER_ANGLE_LOW, 0);
        launcherAngleMid = sharedPreferences.getInt(LAUNCHER_ANGLE_MID, 0);
        launcherAngleHigh = sharedPreferences.getInt(LAUNCHER_ANGLE_HIGH, 0);
    }

    public void save() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ALLIANCE_COLOR, allianceColor.name());
        editor.putString(STARTING_POSITION, startingPosition.name());
        editor.putInt(MAX_LIFT_TICKS, maxLiftTicks);
        editor.putInt(LAUNCHER_ANGLE_LOW, launcherAngleLow);
        editor.putInt(LAUNCHER_ANGLE_MID, launcherAngleMid);
        editor.putInt(LAUNCHER_ANGLE_HIGH, launcherAngleHigh);

        editor.apply(); // If doesn't work use `editor.commit()`
    }


}
