package cz.mendelu.project_xdivis1.extensions

fun Double.round(): String{
    return String.format("%.2f", this)
}