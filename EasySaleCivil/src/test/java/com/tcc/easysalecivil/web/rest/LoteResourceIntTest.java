package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.Lote;
import com.tcc.easysalecivil.repository.LoteRepository;
import com.tcc.easysalecivil.service.LoteService;
import com.tcc.easysalecivil.repository.search.LoteSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LoteResource REST controller.
 *
 * @see LoteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class LoteResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAA";
    private static final String UPDATED_CODIGO = "BBBBB";
    private static final String DEFAULT_RUA = "AAAAA";
    private static final String UPDATED_RUA = "BBBBB";

    private static final BigDecimal DEFAULT_TAMANHO_TERRENO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAMANHO_TERRENO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAMANHO_FRENTE_TERRENO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAMANHO_FRENTE_TERRENO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISTANCIA_PORTARIA = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCIA_PORTARIA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCIA_ESQUINA_MAIS_PROXIMA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ELEVACAO_TERRENO = new BigDecimal(1);
    private static final BigDecimal UPDATED_ELEVACAO_TERRENO = new BigDecimal(2);
    private static final String DEFAULT_POSICAO_RELACAO_AO_SOL = "AAAAA";
    private static final String UPDATED_POSICAO_RELACAO_AO_SOL = "BBBBB";

    private static final BigDecimal DEFAULT_DISTANCIA_AREA_LAZER = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCIA_AREA_LAZER = new BigDecimal(2);
    private static final String DEFAULT_QUADRA = "AAAAA";
    private static final String UPDATED_QUADRA = "BBBBB";

    private static final BigDecimal DEFAULT_VALOR_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_TOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VALOR_ENTRADA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR_ENTRADA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAXA_JUROS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAXA_JUROS = new BigDecimal(2);

    @Inject
    private LoteRepository loteRepository;

    @Inject
    private LoteService loteService;

    @Inject
    private LoteSearchRepository loteSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLoteMockMvc;

    private Lote lote;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoteResource loteResource = new LoteResource();
        ReflectionTestUtils.setField(loteResource, "loteService", loteService);
        this.restLoteMockMvc = MockMvcBuilders.standaloneSetup(loteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        loteSearchRepository.deleteAll();
        lote = new Lote();
        lote.setCodigo(DEFAULT_CODIGO);
        lote.setRua(DEFAULT_RUA);
        lote.setTamanhoTerreno(DEFAULT_TAMANHO_TERRENO);
        lote.setTamanhoFrenteTerreno(DEFAULT_TAMANHO_FRENTE_TERRENO);
        lote.setDistanciaPortaria(DEFAULT_DISTANCIA_PORTARIA);
        lote.setDistanciaEsquinaMaisProxima(DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA);
        lote.setElevacaoTerreno(DEFAULT_ELEVACAO_TERRENO);
        lote.setPosicaoRelacaoAoSol(DEFAULT_POSICAO_RELACAO_AO_SOL);
        lote.setDistanciaAreaLazer(DEFAULT_DISTANCIA_AREA_LAZER);
        lote.setQuadra(DEFAULT_QUADRA);
        lote.setValorTotal(DEFAULT_VALOR_TOTAL);
        lote.setValorEntrada(DEFAULT_VALOR_ENTRADA);
        lote.setTaxaJuros(DEFAULT_TAXA_JUROS);
    }

    @Test
    @Transactional
    public void createLote() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isCreated());

        // Validate the Lote in the database
        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeCreate + 1);
        Lote testLote = lotes.get(lotes.size() - 1);
        assertThat(testLote.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testLote.getRua()).isEqualTo(DEFAULT_RUA);
        assertThat(testLote.getTamanhoTerreno()).isEqualTo(DEFAULT_TAMANHO_TERRENO);
        assertThat(testLote.getTamanhoFrenteTerreno()).isEqualTo(DEFAULT_TAMANHO_FRENTE_TERRENO);
        assertThat(testLote.getDistanciaPortaria()).isEqualTo(DEFAULT_DISTANCIA_PORTARIA);
        assertThat(testLote.getDistanciaEsquinaMaisProxima()).isEqualTo(DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA);
        assertThat(testLote.getElevacaoTerreno()).isEqualTo(DEFAULT_ELEVACAO_TERRENO);
        assertThat(testLote.getPosicaoRelacaoAoSol()).isEqualTo(DEFAULT_POSICAO_RELACAO_AO_SOL);
        assertThat(testLote.getDistanciaAreaLazer()).isEqualTo(DEFAULT_DISTANCIA_AREA_LAZER);
        assertThat(testLote.getQuadra()).isEqualTo(DEFAULT_QUADRA);
        assertThat(testLote.getValorTotal()).isEqualTo(DEFAULT_VALOR_TOTAL);
        assertThat(testLote.getValorEntrada()).isEqualTo(DEFAULT_VALOR_ENTRADA);
        assertThat(testLote.getTaxaJuros()).isEqualTo(DEFAULT_TAXA_JUROS);

        // Validate the Lote in ElasticSearch
        Lote loteEs = loteSearchRepository.findOne(testLote.getId());
        assertThat(loteEs).isEqualToComparingFieldByField(testLote);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setCodigo(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setRua(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTamanhoTerrenoIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setTamanhoTerreno(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTamanhoFrenteTerrenoIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setTamanhoFrenteTerreno(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanciaPortariaIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setDistanciaPortaria(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkElevacaoTerrenoIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setElevacaoTerreno(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPosicaoRelacaoAoSolIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setPosicaoRelacaoAoSol(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanciaAreaLazerIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setDistanciaAreaLazer(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuadraIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setQuadra(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setValorTotal(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setValorEntrada(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxaJurosIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setTaxaJuros(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lote)))
                .andExpect(status().isBadRequest());

        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotes() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get all the lotes
        restLoteMockMvc.perform(get("/api/lotes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
                .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
                .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA.toString())))
                .andExpect(jsonPath("$.[*].tamanhoTerreno").value(hasItem(DEFAULT_TAMANHO_TERRENO.intValue())))
                .andExpect(jsonPath("$.[*].tamanhoFrenteTerreno").value(hasItem(DEFAULT_TAMANHO_FRENTE_TERRENO.intValue())))
                .andExpect(jsonPath("$.[*].distanciaPortaria").value(hasItem(DEFAULT_DISTANCIA_PORTARIA.intValue())))
                .andExpect(jsonPath("$.[*].distanciaEsquinaMaisProxima").value(hasItem(DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA.intValue())))
                .andExpect(jsonPath("$.[*].elevacaoTerreno").value(hasItem(DEFAULT_ELEVACAO_TERRENO.intValue())))
                .andExpect(jsonPath("$.[*].posicaoRelacaoAoSol").value(hasItem(DEFAULT_POSICAO_RELACAO_AO_SOL.toString())))
                .andExpect(jsonPath("$.[*].distanciaAreaLazer").value(hasItem(DEFAULT_DISTANCIA_AREA_LAZER.intValue())))
                .andExpect(jsonPath("$.[*].quadra").value(hasItem(DEFAULT_QUADRA.toString())))
                .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
                .andExpect(jsonPath("$.[*].valorEntrada").value(hasItem(DEFAULT_VALOR_ENTRADA.intValue())))
                .andExpect(jsonPath("$.[*].taxaJuros").value(hasItem(DEFAULT_TAXA_JUROS.intValue())));
    }

    @Test
    @Transactional
    public void getLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", lote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.rua").value(DEFAULT_RUA.toString()))
            .andExpect(jsonPath("$.tamanhoTerreno").value(DEFAULT_TAMANHO_TERRENO.intValue()))
            .andExpect(jsonPath("$.tamanhoFrenteTerreno").value(DEFAULT_TAMANHO_FRENTE_TERRENO.intValue()))
            .andExpect(jsonPath("$.distanciaPortaria").value(DEFAULT_DISTANCIA_PORTARIA.intValue()))
            .andExpect(jsonPath("$.distanciaEsquinaMaisProxima").value(DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA.intValue()))
            .andExpect(jsonPath("$.elevacaoTerreno").value(DEFAULT_ELEVACAO_TERRENO.intValue()))
            .andExpect(jsonPath("$.posicaoRelacaoAoSol").value(DEFAULT_POSICAO_RELACAO_AO_SOL.toString()))
            .andExpect(jsonPath("$.distanciaAreaLazer").value(DEFAULT_DISTANCIA_AREA_LAZER.intValue()))
            .andExpect(jsonPath("$.quadra").value(DEFAULT_QUADRA.toString()))
            .andExpect(jsonPath("$.valorTotal").value(DEFAULT_VALOR_TOTAL.intValue()))
            .andExpect(jsonPath("$.valorEntrada").value(DEFAULT_VALOR_ENTRADA.intValue()))
            .andExpect(jsonPath("$.taxaJuros").value(DEFAULT_TAXA_JUROS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLote() throws Exception {
        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLote() throws Exception {
        // Initialize the database
        loteService.save(lote);

        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Update the lote
        Lote updatedLote = new Lote();
        updatedLote.setId(lote.getId());
        updatedLote.setCodigo(UPDATED_CODIGO);
        updatedLote.setRua(UPDATED_RUA);
        updatedLote.setTamanhoTerreno(UPDATED_TAMANHO_TERRENO);
        updatedLote.setTamanhoFrenteTerreno(UPDATED_TAMANHO_FRENTE_TERRENO);
        updatedLote.setDistanciaPortaria(UPDATED_DISTANCIA_PORTARIA);
        updatedLote.setDistanciaEsquinaMaisProxima(UPDATED_DISTANCIA_ESQUINA_MAIS_PROXIMA);
        updatedLote.setElevacaoTerreno(UPDATED_ELEVACAO_TERRENO);
        updatedLote.setPosicaoRelacaoAoSol(UPDATED_POSICAO_RELACAO_AO_SOL);
        updatedLote.setDistanciaAreaLazer(UPDATED_DISTANCIA_AREA_LAZER);
        updatedLote.setQuadra(UPDATED_QUADRA);
        updatedLote.setValorTotal(UPDATED_VALOR_TOTAL);
        updatedLote.setValorEntrada(UPDATED_VALOR_ENTRADA);
        updatedLote.setTaxaJuros(UPDATED_TAXA_JUROS);

        restLoteMockMvc.perform(put("/api/lotes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLote)))
                .andExpect(status().isOk());

        // Validate the Lote in the database
        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeUpdate);
        Lote testLote = lotes.get(lotes.size() - 1);
        assertThat(testLote.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testLote.getRua()).isEqualTo(UPDATED_RUA);
        assertThat(testLote.getTamanhoTerreno()).isEqualTo(UPDATED_TAMANHO_TERRENO);
        assertThat(testLote.getTamanhoFrenteTerreno()).isEqualTo(UPDATED_TAMANHO_FRENTE_TERRENO);
        assertThat(testLote.getDistanciaPortaria()).isEqualTo(UPDATED_DISTANCIA_PORTARIA);
        assertThat(testLote.getDistanciaEsquinaMaisProxima()).isEqualTo(UPDATED_DISTANCIA_ESQUINA_MAIS_PROXIMA);
        assertThat(testLote.getElevacaoTerreno()).isEqualTo(UPDATED_ELEVACAO_TERRENO);
        assertThat(testLote.getPosicaoRelacaoAoSol()).isEqualTo(UPDATED_POSICAO_RELACAO_AO_SOL);
        assertThat(testLote.getDistanciaAreaLazer()).isEqualTo(UPDATED_DISTANCIA_AREA_LAZER);
        assertThat(testLote.getQuadra()).isEqualTo(UPDATED_QUADRA);
        assertThat(testLote.getValorTotal()).isEqualTo(UPDATED_VALOR_TOTAL);
        assertThat(testLote.getValorEntrada()).isEqualTo(UPDATED_VALOR_ENTRADA);
        assertThat(testLote.getTaxaJuros()).isEqualTo(UPDATED_TAXA_JUROS);

        // Validate the Lote in ElasticSearch
        Lote loteEs = loteSearchRepository.findOne(testLote.getId());
        assertThat(loteEs).isEqualToComparingFieldByField(testLote);
    }

    @Test
    @Transactional
    public void deleteLote() throws Exception {
        // Initialize the database
        loteService.save(lote);

        int databaseSizeBeforeDelete = loteRepository.findAll().size();

        // Get the lote
        restLoteMockMvc.perform(delete("/api/lotes/{id}", lote.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean loteExistsInEs = loteSearchRepository.exists(lote.getId());
        assertThat(loteExistsInEs).isFalse();

        // Validate the database is empty
        List<Lote> lotes = loteRepository.findAll();
        assertThat(lotes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLote() throws Exception {
        // Initialize the database
        loteService.save(lote);

        // Search the lote
        restLoteMockMvc.perform(get("/api/_search/lotes?query=id:" + lote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].rua").value(hasItem(DEFAULT_RUA.toString())))
            .andExpect(jsonPath("$.[*].tamanhoTerreno").value(hasItem(DEFAULT_TAMANHO_TERRENO.intValue())))
            .andExpect(jsonPath("$.[*].tamanhoFrenteTerreno").value(hasItem(DEFAULT_TAMANHO_FRENTE_TERRENO.intValue())))
            .andExpect(jsonPath("$.[*].distanciaPortaria").value(hasItem(DEFAULT_DISTANCIA_PORTARIA.intValue())))
            .andExpect(jsonPath("$.[*].distanciaEsquinaMaisProxima").value(hasItem(DEFAULT_DISTANCIA_ESQUINA_MAIS_PROXIMA.intValue())))
            .andExpect(jsonPath("$.[*].elevacaoTerreno").value(hasItem(DEFAULT_ELEVACAO_TERRENO.intValue())))
            .andExpect(jsonPath("$.[*].posicaoRelacaoAoSol").value(hasItem(DEFAULT_POSICAO_RELACAO_AO_SOL.toString())))
            .andExpect(jsonPath("$.[*].distanciaAreaLazer").value(hasItem(DEFAULT_DISTANCIA_AREA_LAZER.intValue())))
            .andExpect(jsonPath("$.[*].quadra").value(hasItem(DEFAULT_QUADRA.toString())))
            .andExpect(jsonPath("$.[*].valorTotal").value(hasItem(DEFAULT_VALOR_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].valorEntrada").value(hasItem(DEFAULT_VALOR_ENTRADA.intValue())))
            .andExpect(jsonPath("$.[*].taxaJuros").value(hasItem(DEFAULT_TAXA_JUROS.intValue())));
    }
}
