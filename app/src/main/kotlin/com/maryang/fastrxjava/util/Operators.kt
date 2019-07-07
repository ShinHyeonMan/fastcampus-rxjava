package com.maryang.fastrxjava.util

import com.maryang.fastrxjava.entity.GithubRepo
import com.maryang.fastrxjava.entity.Sample
import com.maryang.fastrxjava.entity.User
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors


object Operators {

    fun kotlinOperatores() {
        // 블록에 인자로 넘기나 let
        // 블록에 리시버로 넘기나 with
        // 리턴값은 마지막에 선언한 것
        // 리턴값은 자기 자신

        listOf<User>().let {
            it.forEach {

            }
        }

        with(listOf<User>()) {

            forEach {

            }
        }

        listOf<User>().run {
            map {
                it.name
            }

            1
        }.run {

        }

        listOf<User>().apply {
            forEach {

            }
        }

        Sample().apply {
            data = 3
        }

        Sample().also {
            it.data
        }.run {

        }
    }

    fun mapExplain() {

        // 기존 방식
        val list = listOf<User>()
        val list2 = mutableListOf<String>()
        list.forEach {
            list2.add(it.name)
        }

        // map
        listOf<User>().map {
            it.name
        }.run {

        }

        // flatmap
        listOf<User>().flatMap {
            listOf(it.name)
        }


        // filter
        listOf<GithubRepo>().filter {
            it.star
        }

        listOf<GithubRepo>().find {
            it.star
        }?.run {

        }

        /**
         * first
         * number = 1
         * acc = 0
         * acc +number = 1
         */

        /**
         * second
         * number = 2
         * acc = 1
         * acc + number = 3
         */

        /**
         * third
         * number = 3
         * acc = 3
         * acc + number = 6
         */
        listOf(1, 2, 3).reduce { acc, number ->
            acc + number
        }

        /**
         * first
         * initial = 10
         * number = 1
         * acc = 0
         * initial + acc + number = 11
         */

        /**
         * second
         * number = 2
         * acc = 11
         * acc + number = 13
         */

        /**
         * third
         * number = 3
         * acc = 13
         * acc + number = 16
         */
        listOf(1, 2, 3).fold(10) { acc, number ->
            acc + number
        }

        listOf(listOf(1, 2, 3)).flatten().run {

        }

        // 전부 it 이였을 경우
        listOf(1, 2, 3).all {
            it == 1
        }.run {

        }

        // 하나라도 아니였을 경우
        listOf(1, 2, 3).any {
            it == 1
        }.run {

        }

        // 중복을 제거
        listOf(1, 1, 2, 2, 3, 4, 5).distinct().run {

        }

        // 조건에 맞는 것을 그룹으로
        listOf(1, 1, 2, 2, 3, 4, 5).groupBy {
            it == 1
        }.run {
            // map(true) = listOf(1,1)
            // map(false) = listOf(2,2,3,4,5)
        }
    }

    fun rxJavaExplain() {

        Single.just(true)
            // 여기까지는 true를 반환하는 Single
            .map { false }
        // 여기부터는 false를 반환하는 Single

        Single.just(true).flatMap {
            Single.just(false)
        }

        Single.concat(
            Single.just(listOf(1, 1, 1)),
            Single.just(listOf(2, 2))
        ).subscribe {
            // 1, 1, 1, 2, 2
        }

        Single.merge(
            Single.just(listOf(1, 1, 1)),
            Single.just(listOf(2, 2))
        ).subscribe {
            // 1, 2, 2, 1, 1
        }

        Single.zip<Boolean, Int, String>(
            Single.just(true),
            Single.just(1),
            BiFunction { first, second ->
                ""
            }
        ).subscribe({


        }, {


        })
    }

    fun thread() {
        Executors.newCachedThreadPool()
    }

    fun <T> applySchedulers(observable: Single<T>): Single<T> {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulers(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    val schedulersTransformer = SingleTransformer<Any, Any> {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> applySchedulersRecycle(): SingleTransformer<T, T> {
        return schedulersTransformer as SingleTransformer<T, T>
    }
}

fun <T> Single<T>.applySchedulersExtension(): Single<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
