package br.emerson.pi4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
                val fra2 = listProdFrag.newInstance("main")
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, fra2).commit()
            }

            false
        }
    }

    fun carregarTela1()
    {
        val frag = FragMain.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menucima, menu)

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Opções do menu de cima
        if ( item.itemId == R.id.menuCarrinho )
        {
            this.carregarTela1() // Temporário, apenas carregando esse fragmento para testar se a opção funciona
            return super.onOptionsItemSelected(item)
        }

        if ( item.itemId == R.id.menuConta )
        {
            this.carregarTela1() // Temporário, apenas carregando esse fragmento para testar se a opção funciona
            return super.onOptionsItemSelected(item)
        }


        // Ações das opções da navigationview
        toggle?.let {
            return it.onOptionsItemSelected(item)
        }

        return false
    }
}