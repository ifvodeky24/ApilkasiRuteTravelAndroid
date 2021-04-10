package com.example.aplikasirutetravel.data

import androidx.lifecycle.LiveData
import com.example.aplikasirutetravel.data.source.local.entity.KondisiJalanEntity
import com.example.aplikasirutetravel.data.source.local.entity.PerusahaanEntity
import com.example.aplikasirutetravel.data.source.local.entity.TrayekEntity
import com.example.aplikasirutetravel.vo.Resource

interface TravelDataSource {

    fun getAllPerusahaan(): LiveData<Resource<List<PerusahaanEntity>>>

    fun getAllTrayek(): LiveData<Resource<List<TrayekEntity>>>

    fun getPerusahaanById(id_perusahaan: String): LiveData<Resource<PerusahaanEntity>>

    fun getAllKondisiJalan(): LiveData<Resource<List<KondisiJalanEntity>>>

    fun getAllKondisiJalanSearch(query: String): LiveData<Resource<List<KondisiJalanEntity>>>

    fun getKondisiJalanById(id_kondisi_jalan: String): LiveData<Resource<KondisiJalanEntity>>
}