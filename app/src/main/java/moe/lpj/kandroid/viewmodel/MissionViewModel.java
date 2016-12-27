package moe.lpj.kandroid.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import kandroid.data.FleetData;
import kandroid.data.FleetManager;
import kandroid.data.KCDatabase;
import kandroid.data.Start2Data;
import kandroid.observer.kcsapi.api_port;
import kandroid.thread.Threads;
import kandroid.utils.IDDictionary;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class MissionViewModel extends BaseObservable {

    final static Logger log = LoggerFactory.getLogger(MissionViewModel.class);

    private MissionViewModel() {
        api_port.port.INSTANCE.addListener(new Function0<Unit>() {
            @Override
            public Unit invoke() {
                log.info("Listening");
                IDDictionary<Start2Data.MasterMissionData> masterMissionData = KCDatabase.INSTANCE.getMaster().getMasterMissionData();
                FleetManager fleets = KCDatabase.INSTANCE.getFleets();
                FleetData fleetData = fleets.get(2);
                boolean isMission2Begin = fleetData.getExpeditionState() == 1;
                if (isMission2Begin) {
                    mission2Name = masterMissionData.get(fleetData.getExpeditionDestination()).getName();
                    mission2Time = fleetData.getExpeditionTime();
                }
                MissionViewModel.this.isMission2Begin = isMission2Begin;
                return null;
            }
        });
        Threads.INSTANCE.runTickTack(new Runnable() {
            @Override
            public void run() {
                notifyChange();
            }
        });
    }

    public final static class Holder {
        public final static MissionViewModel Instance = new MissionViewModel();
    }

    private boolean isMission2Begin;
    private String mission2Name;
    private Date mission2Time;

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
        return "null";
    }

    @Bindable
    public String getMission3Time() {
        return "null";
    }

    @Bindable
    public String getMission3LeftTime() {
        return "null";
    }

    @Bindable
    public String getMission4Name() {
        return "null";
    }

    @Bindable
    public String getMission4Time() {
        return "null";
    }

    @Bindable
    public String getMission4LeftTime() {
        return "null";
    }
}
