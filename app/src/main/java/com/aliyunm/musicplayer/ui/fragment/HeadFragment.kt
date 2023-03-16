package com.aliyunm.musicplayer.ui.fragment

import android.os.Bundle
import com.aliyunm.common.ui.BaseFragment
import com.aliyunm.musicplayer.databinding.FragmentHeadBinding
import com.aliyunm.musicplayer.viewmodel.HeadViewModel

class HeadFragment : BaseFragment<FragmentHeadBinding, HeadViewModel>() {

    companion object {
        fun newInstance(title : String): HeadFragment {
            val args = Bundle()
            args.putString("title", title)
            val fragment = HeadFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun setBinding(): FragmentHeadBinding {
        return FragmentHeadBinding.inflate(layoutInflater)
    }

    override fun setViewModel(): Class<HeadViewModel> {
        return HeadViewModel::class.java
    }

    private var title : String = ""

    override fun initData(savedInstanceState: Bundle?) {
        title = arguments?.getString("title", "") ?: ""
    }

    override fun initView() {
        viewBinding.apply {
            ivBack.setOnClickListener {
                requireActivity().finish()
            }
            tvTitle.text = title
        }
    }
}