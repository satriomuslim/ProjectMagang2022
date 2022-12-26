package com.qatros.binamurid.ui.pedagogue.navigation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qatros.binamurid.ui.history.HistoryFragment
import com.qatros.binamurid.ui.pedagogue.daily.DailyPedagogueFragment
import com.qatros.binamurid.ui.pedagogue.daily.DailyPedagogueViewModel
import com.qatros.binamurid.ui.pedagogue.home.HomePedagogueFragment
import com.qatros.binamurid.ui.profile.ProfileFragment
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityNavigationPedagogueBinding
import org.koin.android.ext.android.inject

class NavigationPedagogueActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNavigationPedagogueBinding

    private val viewModel: DailyPedagogueViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationPedagogueBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeIndicatorCurrent().observe(this) {
            Log.e("TAG", "observeData: ", )
            it.getContentIfNotHandled()?.let { success ->
                if(success) {
                    setCurrentFragment(HomePedagogueFragment())
                    binding.navView.selectedItemId = R.id.navigation_home
                }
            }
        }
    }

    private fun init(){
        val homeFragment = HomePedagogueFragment()
        val dailyReportFragment = DailyPedagogueFragment()
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
            replace(R.id.nav_host_fragment_activity_main, fragment)
            commit()
        }
}