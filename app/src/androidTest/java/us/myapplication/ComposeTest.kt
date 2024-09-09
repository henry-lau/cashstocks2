package us.myapplication

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import us.myapplication.view.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ComposeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_launches() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.henry_test_app)).assertExists()
    }


    @Test
    fun test_openStocks() {

        println(composeTestRule.onRoot().printToString())
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.stock_btn), substring = false).performClick()

        println(composeTestRule.onRoot().printToString())
        try {
            composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.api), substring = true).assertExists()
        } catch (e: AssertionError) {
            println(composeTestRule.onRoot().printToString())
            throw e
        }
    }

    //https://medium.com/androiddevelopers/alternatives-to-idling-resources-in-compose-tests-8ae71f9fc473
    @Test
    fun test_openNoStocks() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.no_stock_btn), substring = false).performClick()
        // Wait until there's one element with a "Welcome" text
        val timeoutMillis: Long = 1_000L
        composeTestRule.waitUntil(timeoutMillis) {
            composeTestRule
                .onAllNodesWithText(composeTestRule.activity.getString(R.string.no_stock))
                .fetchSemanticsNodes().size == 1
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.no_stock)).assertExists()

    }

    @Test
    fun test_backNavigation() {
        composeTestRule.onNodeWithText(text = composeTestRule.activity.getString(R.string.error_btn), substring = false).performClick()
        composeTestRule.onNodeWithText(text = composeTestRule.activity.getString(R.string.back), substring = false).performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.henry_test_app)).assertExists()
    }
}