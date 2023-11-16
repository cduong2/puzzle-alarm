package com.example.zooalarm;

import static org.junit.Assert.assertEquals;

import com.example.zooalarm.database.Alarm;
import com.example.zooalarm.ui.fragments.AlarmListFragment;



import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AlarmUnitTest {
    @Test
    public void sort_empty_alarm_list(){
        List<Alarm> original= new ArrayList<>();
        List<Alarm> expected= new ArrayList<>();

        AlarmListFragment.sortByTime(original);
        assertEquals(expected,original);
    }

    @Test
    public void sort_am_pm_alarm_list(){
        List<Alarm> original= new ArrayList<>();
        List<Alarm> expected= new ArrayList<>();

        Alarm first= new Alarm();
        Alarm second= new Alarm();
        Alarm third= new Alarm();
        //alarms should be in this order after sorting
        first.setTime("10:05 AM");
        second.setTime("10:02 PM");
        third.setTime("11:03 PM");
        //alarms inserted out of order
        original.add(second);
        original.add(third);
        original.add(first);
        //alarms inserted in order for the expected
        expected.add(first);
        expected.add(second);
        expected.add(third);

        AlarmListFragment.sortByTime(original);
        assertEquals(expected,original);
    }

    @Test
    public void sort_minutes_alarm_list(){
        List<Alarm> original= new ArrayList<>();
        List<Alarm> expected= new ArrayList<>();

        Alarm first= new Alarm();
        Alarm second= new Alarm();
        Alarm third= new Alarm();
        //alarms should be in this order after sorting
        first.setTime("10:01 AM");
        second.setTime("10:02 AM");
        third.setTime("10:30 AM");
        //alarms inserted out of order
        original.add(third);
        original.add(second);
        original.add(first);

        //alarms inserted in order for the expected
        expected.add(first);
        expected.add(second);
        expected.add(third);

        AlarmListFragment.sortByTime(original);
        assertEquals(expected,original);
    }

    @Test
    public void sort_hours_alarm_list(){
        List<Alarm> original= new ArrayList<>();
        List<Alarm> expected= new ArrayList<>();

        Alarm first= new Alarm();
        Alarm second= new Alarm();
        Alarm third= new Alarm();
        //alarms should be in this order after sorting
        first.setTime("2:01 AM");
        second.setTime("10:02 AM");
        third.setTime("3:30 PM");
        //alarms inserted out of order
        original.add(second);
        original.add(third);
        original.add(first);

        //alarms inserted in order for the expected
        expected.add(first);
        expected.add(second);
        expected.add(third);

        AlarmListFragment.sortByTime(original);
        assertEquals(expected,original);
    }


}