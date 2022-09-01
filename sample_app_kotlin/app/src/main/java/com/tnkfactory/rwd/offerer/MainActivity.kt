package com.tnkfactory.rwd.offerer

import android.os.Bundle
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tnkfactory.ad.TnkSession
import com.tnkfactory.rwd.offerer.databinding.ActivityMainBinding
import com.tnkfactory.rwd.offerer.databinding.RowMenuItemBinding
import com.tnkfactory.rwd.offerer.utils.GAIDTask
import com.tnkfactory.rwd.offerer.utils.TnkProgressDialog
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.databinding.BindableItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // TNK SDK 기본 설정
        initTnkSdk()


        menuItem(binding.rvMainContent)


    }


    private fun initTnkSdk() {

        val progress = TnkProgressDialog(this@MainActivity)
        progress.show()

        lifecycleScope.launch(Dispatchers.IO) {

            // Analytics Report 사용을 위한 설정
            TnkSession.applicationStarted(this@MainActivity)

            // 유저 식별 값 설정
            // 샘플에서는 GAID를 사용해 유저 식별 값을 설정했으나 GAID는 초기화가 가능한 값이므로
            // 사용자가 변경 불가능한 값을 사용해 설정하는 것을 추천합니다.
            val gaid = GAIDTask().getGAID(this@MainActivity)
            TnkSession.setUserName(this@MainActivity, gaid)

            // COPPA 설정 (true - ON / false - OFF)
            TnkSession.setCOPPA(this@MainActivity, false)

        }
        progress.dismiss()
    }


    fun menuItem(recyclerView: RecyclerView) {

        val rvAdapter = GroupieAdapter().apply {
            add(MenuItem("basic") { BasicSampleActivity.start(this@MainActivity) })
            add(MenuItem("embed") { EmbedSampleActivity.start(this@MainActivity) })
            add(MenuItem("custom") { CustomSampleActivity.start(this@MainActivity) })
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rvAdapter
        }


    }


    class MenuItem(private val title: String, private val onClick: OnClickListener) : BindableItem<RowMenuItemBinding>() {
        override fun bind(viewBinding: RowMenuItemBinding, position: Int) {
            viewBinding.tvMenuTitle.text = title
            viewBinding.tvMenuTitle.setOnClickListener(onClick)
        }

        override fun getLayout(): Int = R.layout.row_menu_item

    }
}