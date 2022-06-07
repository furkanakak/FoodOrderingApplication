package com.example.foodorderingapplication.ui.setting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentSettingsSecondBinding
import com.example.foodorderingapplication.model.entity.User
import com.example.foodorderingapplication.model.entity.profile.UserRequest
import com.example.foodorderingapplication.ui.profile.ProfileFragment
import com.example.foodorderingapplication.ui.splash.SplashActivity
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingsSecondBinding
    private val viewModel: SettingViewModel by viewModels()
    private var image: Int = R.mipmap.oops_404_foreground
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        addListeners()
    }

    private fun initViews() {
        viewModel.getUser().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    setField(response.data?.user)
                    isSettingVisible(true)
                }
                Resource.Status.ERROR -> {
                    isSettingVisible(false)
                }
            }
        }
    }


    private fun isSettingVisible(isVisible: Boolean) {
        binding.container.isVisible = isVisible
        if (isVisible.not()) {
            AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("There is a problem")
                .setPositiveButton("Cancel") { _, _ ->
                    findNavController().popBackStack()
                }.show()
        }
    }

    private fun getImageView(id: Int): Int {
        return when (id) {
            R.id.avatarRadioButton1 -> R.mipmap.avatar_1_foreground
            R.id.avatarRadioButton2 -> R.mipmap.avatar_2_foreground
            R.id.avatarRadioButton3 -> R.mipmap.avatar_3_foreground
            R.id.avatarRadioButton4 -> R.mipmap.avatar_4_foreground
            R.id.avatarRadioButton5 -> R.mipmap.avatar_5_foreground
            R.id.avatarRadioButton6 -> R.mipmap.avatar_6_foreground
            R.id.avatarRadioButton7 -> R.mipmap.avatar_7_foreground
            R.id.avatarRadioButton8 -> R.mipmap.avatar_8_foreground
            R.id.avatarRadioButton9 -> R.mipmap.avatar_9_foreground

            else -> R.mipmap.oops_404_foreground
        }
    }

    private fun getRadioButtonId(image: Int): Int {
        return when (image) {
            R.mipmap.avatar_1_foreground -> R.id.avatarRadioButton1
            R.mipmap.avatar_2_foreground -> R.id.avatarRadioButton2
            R.mipmap.avatar_3_foreground -> R.id.avatarRadioButton3
            R.mipmap.avatar_4_foreground -> R.id.avatarRadioButton4
            R.mipmap.avatar_5_foreground -> R.id.avatarRadioButton5
            R.mipmap.avatar_6_foreground -> R.id.avatarRadioButton6
            R.mipmap.avatar_7_foreground -> R.id.avatarRadioButton7
            R.mipmap.avatar_8_foreground -> R.id.avatarRadioButton8
            R.mipmap.avatar_9_foreground -> R.id.avatarRadioButton9
            else -> 0
        }
    }

    private fun setField(user: User?) {
        binding.nameEditText.setText(user?.name)
        binding.mailEditText.setText(user?.email)
        binding.addressEditText.setText(user?.address)
        binding.phoneNumberEditText.setText(user?.phone)
        user?.paymentMethod?.let {
            image = ProfileFragment.getImageResource(user.profileImage)
            binding.avatarImageView.setImageResource(ProfileFragment.getImageResource(user.profileImage))
        }
    }

    private fun updateUser() {
        val name = binding.nameEditText.text.toString()
        val mail = binding.mailEditText.text.toString()
        val phone = binding.phoneNumberEditText.text.toString()
        val address = binding.addressEditText.text.toString()


        val user = UserRequest(mail, name, address, phone, image.toString(), 1)
        viewModel.updateUser(user).observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.LOADING -> {
                    binding.profileProgressBarr
                }
                Resource.Status.SUCCESS -> {
                    findNavController().navigate(R.id.action_settingFragment_to_profileFragment)
                    isSettingVisible(true)
                }
                Resource.Status.ERROR -> {
                    isSettingVisible(false)
                }
            }
        }
    }

    private fun addListeners() {
        binding.avatarImageView.setOnClickListener { changeAvatar(it) }
        binding.profileChangee.setOnClickListener { updateUser() }
        binding.logOut.setOnClickListener {
            viewModel.logOut()
            val intent = Intent(context, SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun changeAvatar(view: View) {
        val design: View = layoutInflater.inflate(R.layout.item_avatar_select, null)
        val radioGroup: RadioGroup = design.findViewById(R.id.avatarRadioGroup)
        val builder = AlertDialog.Builder(view.context)
        builder.setView(design)

        val selectedId = getRadioButtonId(image)
        radioGroup.check(selectedId)
        radioGroup.setOnCheckedChangeListener { _, _ ->
            image = getImageView(radioGroup.checkedRadioButtonId)
        }
        builder.setPositiveButton(getString(R.string.save)) { _: DialogInterface, _: Int ->
            binding.avatarImageView.setImageResource(image)
        }
        builder.show()
    }
}