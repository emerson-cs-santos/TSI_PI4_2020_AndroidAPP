package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var toggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.toggle = ActionBarDrawerToggle( this, drawerLayout, R.string.open, R.string.close )

        this.toggle?.let {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        this.carregarTela1()

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            if ( it.itemId == R.id.principal )
            {
                this.carregarTela1()
                true
            }

            if ( it.itemId == R.id.jogos )
            {
                val fra2 = listProdFrag.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, fra2).commit()
            }

            false
        }
    }

    fun carregarTela1()
    {
        val frag = fragMain.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toggle?.let {
            return it.onOptionsItemSelected(item)
        }

        return false
    }
}