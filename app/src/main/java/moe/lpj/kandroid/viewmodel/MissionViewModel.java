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

    public final static class Holder {
        public final static MissionViewModel Instance = new MissionViewModel();
    }

    boolean isMission2Begin;
    String mission2Name;
    Date mission2Time;

    boolean isMission3Begin;
    String mission3Name;
    Date mission3Time;

    boolean isMission4Begin;
    String mission4Name;
    Date mission4Time;

    @Bindable
    public String getMission2Name() {
        if (isMission2Begin) {
            return mission2Name;
        }
        return "null";
    }

    @Bindable
    public String getMission2Time() {
        if (isMission2Begin) {
            return DateFormatUtils.format(mission2Time, "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission2LeftTime() {
        if (isMission2Begin) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), mission2Time.getTime(), "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission3Name() {
        if (isMission3Begin) {
            return mission3Name;
        }
        return "null";
    }

    @Bindable
    public String getMission3Time() {
        if (isMission3Begin) {
            return DateFormatUtils.format(mission3Time, "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission3LeftTime() {
        if (isMission3Begin) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), mission2Time.getTime(), "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission4Name() {
        if (isMission4Begin) {
            return mission2Name;
        }
        return "null";
    }

    @Bindable
    public String getMission4Time() {
        if (isMission4Begin) {
            return DateFormatUtils.format(mission2Time, "HH:mm:ss");
        }
        return "null";
    }

    @Bindable
    public String getMission4LeftTime() {
        if (isMission4Begin) {
            return DurationFormatUtils.formatPeriod(new Date().getTime(), mission2Time.getTime(), "HH:mm:ss");
        }
        return "null";
    }
}
