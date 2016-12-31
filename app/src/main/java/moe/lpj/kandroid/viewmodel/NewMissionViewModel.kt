package moe.lpj.kandroid.viewmodel

import android.databinding.ObservableArrayList
import java.util.*

object NewMissionViewModel {

    val times = ObservableArrayList<Date>()
    val names = ObservableArrayList<String>()

    fun some(date: Date) {}
}