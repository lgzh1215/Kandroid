package moe.lpj.kandroid.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.Date;

import kandroid.observer.kcsapi.api_get_member;
import kandroid.observer.kcsapi.api_port;

public class MissionViewModel extends BaseObservable {

    private MissionViewModel() {
        api_port.port.INSTANCE.addListener(UtilsKt.getMissionViewModelUpdater());
        api_get_member.deck.INSTANCE.addListener(UtilsKt.getMissionViewModelUpdater());
    }

    private final static class Holder {
        final static MissionViewModel Instance = new MissionViewModel();
    }

    public static MissionViewModel getInstance() {
        return Holder.Instance;
    }

    public Date[] missionTime = new Date[3];
    public String[] missionName = new String[3];

    @Bindable
    public String getMission2Name() {
        if (missionTime[0] != null) {
            return missionName[0];
        }
        return "null";
    }

    @Bindable
    public String getMission2Time() {
        if (missionTime[0] != null) {
            return DateFormatUtils.format(missionTime[0], "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission2LeftTime() {
        if (missionTime[0] != null) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), missionTime[0].getTime(), "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission3Name() {
        if (missionTime[1] != null) {
            return missionName[1];
        }
        return "null";
    }

    @Bindable
    public String getMission3Time() {
        if (missionTime[1] != null) {
            return DateFormatUtils.format(missionTime[1], "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission3LeftTime() {
        if (missionTime[1] != null) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), missionTime[1].getTime(), "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission4Name() {
        if (missionTime[2] != null) {
            return missionName[2];
        }
        return "null";
    }

    @Bindable
    public String getMission4Time() {
        if (missionTime[2] != null) {
            return DateFormatUtils.format(missionTime[2], "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission4LeftTime() {
        if (missionTime[2] != null) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), missionTime[2].getTime(), "HH:mm:ss");
        }
        return "null";
    }
}
