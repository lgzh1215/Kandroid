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

enum class Searching {
    Undefine, Success, SuccessPlaneNoReturn, FailPlaneNoReturn, Fail, SuccessNoPlane, FailNoPlane;

    companion object {
        fun fromApiInt(api_search: Int): Searching = when (api_search) {
            1 -> Success
            2 -> SuccessNoPlane
            3 -> FailPlaneNoReturn
            4 -> Fail
            5 -> SuccessNoPlane
            6 -> FailNoPlane
            else -> Undefine
        }
    }
}

enum class Formation {
    Undefine, LineAhead, DoubleLine, Diamond, Echelon, LineAbreast,
    Combine1, Combine2, Combine3, Combine4;

    companion object {
        fun fromApiInt(api_formation: Int): Formation = when (api_formation) {
            1 -> LineAhead
            2 -> DoubleLine
            3 -> Diamond
            4 -> Echelon
            5 -> LineAbreast
            11 -> Combine1
            12 -> Combine2
            13 -> Combine3
            14 -> Combine4
            else -> Undefine
        }
    }
}

enum class Engagement {
    Undefine, Parallel, HeadOn, GreenT, RedT;

    companion object {
        fun fromApiInt(api_formation: Int): Engagement = when (api_formation) {
            1 -> Parallel
            2 -> HeadOn
            3 -> GreenT
            4 -> RedT
            else -> Undefine
        }
    }
}

enum class AirSuperiority {

}