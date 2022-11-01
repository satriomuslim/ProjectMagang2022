package com.qatros.qtn_bina_murid.ui.pedagogue.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityNavigationPedagogueBinding
import com.qatros.qtn_bina_murid.ui.history.HistoryFragment
import com.qatros.qtn_bina_murid.ui.pedagogue.daily.DailyPedagogueFragment
import com.qatros.qtn_bina_murid.ui.pedagogue.home.HomePedagogueFragment
import com.qatros.qtn_bina_murid.ui.profile.ProfileActivity

class NavigationPedagogueActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNavigationPedagogueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationPedagogueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        val homeFragment = HomePedagogueFragment()
        val dailyReportFragment = DailyPedagogueFragment()
        val historyFragment = HistoryFragment()
        val parentEditProfileFragment = ProfileActivity()

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