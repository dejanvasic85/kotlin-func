package me.dejanvasic


data class ConsStream<out A>(
    val head: () -> A,
    val tail: () -> Stream<A>,
) : Stream<A>()

object EmptyStream : Stream<Nothing>()

sealed class Stream<out A> {
    companion object {
        fun <A> Stream<A>.headOption(): Option<A> =
            when (this) {
                is EmptyStream -> None
                is ConsStream -> Some(head())
            }
    }
}