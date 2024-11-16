package com.example.core.commonTest

import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.printToString
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.LooperMode.Mode.PAUSED

@RunWith(AndroidJUnit4::class)
@Config(
    sdk = [28],
    qualifiers = "+w320dp-h640dp",
    instrumentedPackages = ["androidx.loader.content"],
)
@LooperMode(PAUSED)
abstract class AndroidTest {
    @get:Rule
    val rule = createComposeRule()
    fun printTree(useUnmergedTree: Boolean = false) {
        println(
            rule.onAllNodes(useUnmergedTree = useUnmergedTree, matcher = isRoot()).onLast()
                .printToString()
        )
    }

}
