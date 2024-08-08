package id.rllyhz.giziplan.utils

val corpus = arrayOf(
    "the house had a tiny little mouse",
    "the cat saw the mouse",
    "the mouse run away from the house",
    "the cat finally ate the mouse",
    "the end of the mouse story",
)

val final_corpus = arrayOf(
    "the", "house", "had", "a", "tiny", "little", "cat",
    "saw", "run", "away", "from", "cat", "finally", "ate",
    "end", "of", "story",
)

val vector1 = arrayOf(
    1, 0, 1, 0
)
val vector2 = arrayOf(
    0, 1, 0, 1
)
val vector3 = arrayOf(
    1, 0, 0, 1
)