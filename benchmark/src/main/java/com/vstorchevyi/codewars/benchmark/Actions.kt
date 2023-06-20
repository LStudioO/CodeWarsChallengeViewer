package com.vstorchevyi.codewars.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.challengesWaitForContent() {
    // Wait for the first challenge
    device.wait(Until.hasObject(By.desc("Completed challenge")), 60_000)
}

fun MacrobenchmarkScope.challengesScrollDownUp() {
    val challengesList = device.findObject(By.res("challengesContainer"))
    device.flingElementDownUp(challengesList)
    device.wait(Until.hasObject(By.desc("Completed challenge")), 60_000)
}

fun MacrobenchmarkScope.goToDetailsScreen() {
    device.findObject(By.desc("Completed challenge")).click()
    device.waitForIdle()
    // Wait until the toolbar is shown
    device.wait(Until.hasObject(By.desc("Details toolbar")), 2_000)
    // Wait until content is loaded by checking if loader is gone
    device.wait(Until.gone(By.desc("Screen loader")), 10_000)
}
