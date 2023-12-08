package com.uth_diseno.hasher

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


class ListFragment : Fragment() {

    private var hashers: MutableList<Hashers> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        initData()

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_new)

        val adapter = HasherAdapter(hashers)

        val recyclerView = view.findViewById<RecyclerView>(R.id.hasherRecycler)
        var _id: Int = 0
        var _tittle: String
        var _text: String
        var _key: String
        var _textEncrypted: String

        fab.setOnClickListener {

            val bottomSheetFragment = BottomSheetDialog(view.context)
            val parentView: View = layoutInflater.inflate(R.layout.bsd_new_hash, null)
            bottomSheetFragment.setContentView(parentView)
            bottomSheetFragment.show()

            // Elementos del formulario bsd
            val newId = parentView.findViewById<EditText>(R.id.bsd_hash_id)
            val newTittle = parentView.findViewById<EditText>(R.id.bsd_hash_tittle)
            val newText = parentView.findViewById<EditText>(R.id.bsd_hash_text)
            val newKey = parentView.findViewById<EditText>(R.id.bsd_hash_key)

            val button = parentView.findViewById<Button>(R.id.bsd_submit)

            button.setOnClickListener {
                _id = newId.text.toString().toInt()
                _tittle = newTittle.text.toString()
                _text = newText.text.toString()
                _key = newKey.text.toString()

                val secretKey = generateSecretKey()

                val encryptedData = encrypt(_text, secretKey)
                _textEncrypted = bytesToHexString(encryptedData)

                val newProductAdd = Hashers(
                    _id,
                    _tittle,
                    _text,
                    _key,
                    _textEncrypted
                )

                hashers.add(newProductAdd)
                adapter.notifyDataSetChanged()

                bottomSheetFragment.dismiss()
            }

        }

        val layoutManager = LinearLayoutManager(container?.context)

        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = adapter

        return view
    }

    private fun initData() {
        hashers = mutableListOf(
            Hashers(1, "Contra Face", "Hola Mundo", "perro", "F3ZS1NO7VU5IB8LY2RP9KH6WT4JMXGC"),
            Hashers(2, "Password", "abcdefg", "gato", "HSRJT8NWQZXY5PFAGVDCM9BU3IO7LKEX"),
            Hashers(3, "Login", "12345678", "123", "LPVRS2GQJU7ZN1STCFMB3VXKR6Y9OAEH"),
            Hashers(4, "Insta", "987654321", "hola", "RLYXO9ZB2SH7TJFPWGDCM1NV5RQ8EX3A"),
            Hashers(5, "TikTok", "Hola Mundo", "erik", "U3LHVY9JX5MB7KO1IGPQ8ZR4STCN2AFE"),
            Hashers(6, "Microsoft", "abcdefg", "casa", "I8SPZR7LB2OK5EX9NM3GU6YV1JH4WQCF"),
            Hashers(7, "Login", "12345678", "hector", "Q6LR8SO9ZT4NY2AB7PK5EG1VH3IXJWCU"),
            Hashers(8, "Mercadolibre", "987654321", "mansion", "Z3NW7AS2XK9PQ1OH8LF4RM5GD6VBTYJU"),
            Hashers(9, "Telegram", "Hola Mundo", "tesla", "M4WO7RP9XG2VU5SD3IA1JF8LB6TYCNZH"),
            Hashers(10, "Laptop", "abcdefg", "google", "X1OY8LT6VP3ZB2SR7JH9MN4GQ5CWUFKD")
        )
    }

    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        val secureRandom = SecureRandom()
        keyGenerator.init(256, secureRandom)
        return keyGenerator.generateKey()
    }

    private fun encrypt(data: String, key: SecretKey): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(data.toByteArray())
    }

    private fun bytesToHexString(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v shr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }
}