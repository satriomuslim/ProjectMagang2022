package com.qatros.binamurid.ui.parent.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qatros.binamurid.ui.history.HistoryFragment
import com.qatros.binamurid.ui.parent.daily.DailyParentFragment
import com.qatros.binamurid.ui.parent.home.HomeParentFragment
import com.qatros.binamurid.ui.profile.ProfileFragment
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityNavigationParentBinding

class NavigationParentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationParentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val homeFragment = HomeParentFragment()
        val dailyReportFragment = DailyParentFragment()
        val historyFragment = HistoryFragment()
        val parentEditProfileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(homeFragment)
                R.id.navigation_daily_report -> setCurrentFragment(dailyReportFragment)
                R.id.navigation_history -> setCurrentFragment(historyFragment)
                R.id.navigation_profile -> setCurrentFragment(parentEditProfileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main_parent, fragment)
            commit()
        }
}