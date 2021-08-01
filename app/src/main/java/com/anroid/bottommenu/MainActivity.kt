package com.anroid.bottommenu

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    init{
        instance = this
    }

    companion object{
        private var instance:MainActivity? = null
        fun getInstance(): MainActivity? {
            return instance
        }
    }

    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_)
    }

    private val bn: BottomNavigationView by lazy {
        findViewById(R.id.bn_)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        bn.setOnNavigationItemSelectedListener {
            replaceFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_review -> ReviewFragment()
                    R.id.menu_mypage -> MypageFragment()
                    R.id.menu_setting -> SettingFragment()
                    else -> LogoutFragment()
                }
            )
            true
        }
}

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    fun reviewToMypage() {
        replaceFragment(MypageFragment())
        bn.menu.getItem(2).isChecked = true
    }

    fun mypageToReview() {
        replaceFragment(ReviewFragment())
        bn.menu.getItem(1).isChecked = true
    }
}
