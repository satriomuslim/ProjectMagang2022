package com.qatros.qtn_bina_murid.ui.parent.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityNavigationParentBinding
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyParentFragment
import com.qatros.qtn_bina_murid.ui.parent.history.HistoryParentFragment
import com.qatros.qtn_bina_murid.ui.parent.home.HomeParentFragment
import com.qatros.qtn_bina_murid.ui.parent.profile.ParentEditProfileFragment

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
        val historyFragment = HistoryParentFragment()
        val parentEditProfileFragment = ParentEditProfileFragment()

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
            replace(R.id.nav_host_fragment_activity_main, fragment)
            commit()
        }
}