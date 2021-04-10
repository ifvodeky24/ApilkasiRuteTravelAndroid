package com.example.aplikasirutetravel.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasirutetravel.R
import com.example.aplikasirutetravel.databinding.FragmentHomeBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val list = ArrayList<Informasi>()

    private val time = 2000
    private var mBackPressed: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvInformasi?.setHasFixedSize(true)

        list.addAll(DataInformasi.listData)
        showRecyclerList()

        binding?.linearLayoutPerusahaan?.setOnClickListener {
            findNavController().navigate(R.id.perusahaanFragment)
        }

        binding?.linearLayoutKondisiJalan?.setOnClickListener {
            findNavController().navigate(R.id.kondisiJalanFragment)
        }

        binding?.linearLayoutAngkutan?.setOnClickListener {
            findNavController().navigate(R.id.angkutanFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    try {
                        if (mBackPressed + time > System.currentTimeMillis()) {
                            activity?.finish()
                        } else {
                            Toast.makeText(
                                activity,
                                "Click two times to exit",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        mBackPressed = System.currentTimeMillis()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )

        checkPermision()

    }

    private fun checkPermision() {
        if (activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
            != PackageManager.PERMISSION_GRANTED || activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_PHONE_STATE
                )
            }
            != PackageManager.PERMISSION_GRANTED || activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
            != PackageManager.PERMISSION_GRANTED || activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            != PackageManager.PERMISSION_GRANTED || activity?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(activity, "need permission", Toast.LENGTH_SHORT).show()

            Dexter.withContext(activity)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) { /* ... */
                        checkPermision()
                    }
                }).check()
        }
    }

    private fun showRecyclerList() {
        binding?.rvInformasi?.layoutManager =
            LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        val listHeroAdapter = InformasiAdapter(list)
        binding?.rvInformasi?.adapter = listHeroAdapter
    }

}