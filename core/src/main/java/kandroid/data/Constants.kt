package kandroid.data

fun GetAdmiralRank(id: Int): String {
    return when (id) {
        1 -> "元帥"
        2 -> "大将"
        3 -> "中将"
        4 -> "少将"
        5 -> "大佐"
        6 -> "中佐"
        7 -> "新米中佐"
        8 -> "少佐"
        9 -> "中堅少佐"
        10 -> "新米少佐"
        else -> "提督"
    }
}