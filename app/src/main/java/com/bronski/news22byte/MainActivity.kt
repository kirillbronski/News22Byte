package com.bronski.news22byte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bronski.news22byte.extension.inTransaction
import com.bronski.news22byte.news.NewsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        displayFragment(NewsFragment.newInstance())
    }

    fun displayFragment(
        fragment: Fragment,
        transaction: FragmentTransaction.() -> FragmentTransaction = {
            replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }
    ) {
        supportFragmentManager.inTransaction {
            transaction.invoke(this)
        }
    }
}