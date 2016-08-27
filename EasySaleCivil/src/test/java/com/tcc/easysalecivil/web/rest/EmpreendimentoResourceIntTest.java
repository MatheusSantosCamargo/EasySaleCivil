package com.tcc.easysalecivil.web.rest;

import com.tcc.easysalecivil.EasySaleCivilApp;
import com.tcc.easysalecivil.domain.Empreendimento;
import com.tcc.easysalecivil.repository.EmpreendimentoRepository;
import com.tcc.easysalecivil.service.EmpreendimentoService;
import com.tcc.easysalecivil.repository.search.EmpreendimentoSearchRepository;

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
 * Test class for the EmpreendimentoResource REST controller.
 *
 * @see EmpreendimentoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EasySaleCivilApp.class)
@WebAppConfiguration
@IntegrationTest
public class EmpreendimentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAA";
    private static final String UPDATED_NOME = "BBBBB";
    private static final String DEFAULT_LOCALIZACAO = "AAAAA";
    private static final String UPDATED_LOCALIZACAO = "BBBBB";

    private static final Boolean DEFAULT_IND_CONDOMINIO_FECHADO = false;
    private static final Boolean UPDATED_IND_CONDOMINIO_FECHADO = true;

    private static final BigDecimal DEFAULT_DISTANCIA_CENTRO_CIDADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISTANCIA_CENTRO_CIDADE = new BigDecimal(2);
    private static final String DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO = "AAAAA";
    private static final String UPDATED_CAMINHO_IMAGEM_EMPREENDIMENTO = "BBBBB";

    @Inject
    private EmpreendimentoRepository empreendimentoRepository;

    @Inject
    private EmpreendimentoService empreendimentoService;

    @Inject
    private EmpreendimentoSearchRepository empreendimentoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEmpreendimentoMockMvc;

    private Empreendimento empreendimento;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EmpreendimentoResource empreendimentoResource = new EmpreendimentoResource();
        ReflectionTestUtils.setField(empreendimentoResource, "empreendimentoService", empreendimentoService);
        this.restEmpreendimentoMockMvc = MockMvcBuilders.standaloneSetup(empreendimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        empreendimentoSearchRepository.deleteAll();
        empreendimento = new Empreendimento();
        empreendimento.setNome(DEFAULT_NOME);
        empreendimento.setLocalizacao(DEFAULT_LOCALIZACAO);
        empreendimento.setIndCondominioFechado(DEFAULT_IND_CONDOMINIO_FECHADO);
        empreendimento.setDistanciaCentroCidade(DEFAULT_DISTANCIA_CENTRO_CIDADE);
        empreendimento.setCaminhoImagemEmpreendimento(DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO);
    }

    @Test
    @Transactional
    public void createEmpreendimento() throws Exception {
        int databaseSizeBeforeCreate = empreendimentoRepository.findAll().size();

        // Create the Empreendimento

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isCreated());

        // Validate the Empreendimento in the database
        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeCreate + 1);
        Empreendimento testEmpreendimento = empreendimentos.get(empreendimentos.size() - 1);
        assertThat(testEmpreendimento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEmpreendimento.getLocalizacao()).isEqualTo(DEFAULT_LOCALIZACAO);
        assertThat(testEmpreendimento.isIndCondominioFechado()).isEqualTo(DEFAULT_IND_CONDOMINIO_FECHADO);
        assertThat(testEmpreendimento.getDistanciaCentroCidade()).isEqualTo(DEFAULT_DISTANCIA_CENTRO_CIDADE);
        assertThat(testEmpreendimento.getCaminhoImagemEmpreendimento()).isEqualTo(DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO);

        // Validate the Empreendimento in ElasticSearch
        Empreendimento empreendimentoEs = empreendimentoSearchRepository.findOne(testEmpreendimento.getId());
        assertThat(empreendimentoEs).isEqualToComparingFieldByField(testEmpreendimento);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empreendimentoRepository.findAll().size();
        // set the field null
        empreendimento.setNome(null);

        // Create the Empreendimento, which fails.

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isBadRequest());

        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocalizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empreendimentoRepository.findAll().size();
        // set the field null
        empreendimento.setLocalizacao(null);

        // Create the Empreendimento, which fails.

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isBadRequest());

        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndCondominioFechadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empreendimentoRepository.findAll().size();
        // set the field null
        empreendimento.setIndCondominioFechado(null);

        // Create the Empreendimento, which fails.

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isBadRequest());

        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDistanciaCentroCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empreendimentoRepository.findAll().size();
        // set the field null
        empreendimento.setDistanciaCentroCidade(null);

        // Create the Empreendimento, which fails.

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isBadRequest());

        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaminhoImagemEmpreendimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empreendimentoRepository.findAll().size();
        // set the field null
        empreendimento.setCaminhoImagemEmpreendimento(null);

        // Create the Empreendimento, which fails.

        restEmpreendimentoMockMvc.perform(post("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(empreendimento)))
                .andExpect(status().isBadRequest());

        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpreendimentos() throws Exception {
        // Initialize the database
        empreendimentoRepository.saveAndFlush(empreendimento);

        // Get all the empreendimentos
        restEmpreendimentoMockMvc.perform(get("/api/empreendimentos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(empreendimento.getId().intValue())))
                .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
                .andExpect(jsonPath("$.[*].localizacao").value(hasItem(DEFAULT_LOCALIZACAO.toString())))
                .andExpect(jsonPath("$.[*].indCondominioFechado").value(hasItem(DEFAULT_IND_CONDOMINIO_FECHADO.booleanValue())))
                .andExpect(jsonPath("$.[*].distanciaCentroCidade").value(hasItem(DEFAULT_DISTANCIA_CENTRO_CIDADE.intValue())))
                .andExpect(jsonPath("$.[*].caminhoImagemEmpreendimento").value(hasItem(DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO.toString())));
    }

    @Test
    @Transactional
    public void getEmpreendimento() throws Exception {
        // Initialize the database
        empreendimentoRepository.saveAndFlush(empreendimento);

        // Get the empreendimento
        restEmpreendimentoMockMvc.perform(get("/api/empreendimentos/{id}", empreendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(empreendimento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.localizacao").value(DEFAULT_LOCALIZACAO.toString()))
            .andExpect(jsonPath("$.indCondominioFechado").value(DEFAULT_IND_CONDOMINIO_FECHADO.booleanValue()))
            .andExpect(jsonPath("$.distanciaCentroCidade").value(DEFAULT_DISTANCIA_CENTRO_CIDADE.intValue()))
            .andExpect(jsonPath("$.caminhoImagemEmpreendimento").value(DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpreendimento() throws Exception {
        // Get the empreendimento
        restEmpreendimentoMockMvc.perform(get("/api/empreendimentos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpreendimento() throws Exception {
        // Initialize the database
        empreendimentoService.save(empreendimento);

        int databaseSizeBeforeUpdate = empreendimentoRepository.findAll().size();

        // Update the empreendimento
        Empreendimento updatedEmpreendimento = new Empreendimento();
        updatedEmpreendimento.setId(empreendimento.getId());
        updatedEmpreendimento.setNome(UPDATED_NOME);
        updatedEmpreendimento.setLocalizacao(UPDATED_LOCALIZACAO);
        updatedEmpreendimento.setIndCondominioFechado(UPDATED_IND_CONDOMINIO_FECHADO);
        updatedEmpreendimento.setDistanciaCentroCidade(UPDATED_DISTANCIA_CENTRO_CIDADE);
        updatedEmpreendimento.setCaminhoImagemEmpreendimento(UPDATED_CAMINHO_IMAGEM_EMPREENDIMENTO);

        restEmpreendimentoMockMvc.perform(put("/api/empreendimentos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEmpreendimento)))
                .andExpect(status().isOk());

        // Validate the Empreendimento in the database
        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeUpdate);
        Empreendimento testEmpreendimento = empreendimentos.get(empreendimentos.size() - 1);
        assertThat(testEmpreendimento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEmpreendimento.getLocalizacao()).isEqualTo(UPDATED_LOCALIZACAO);
        assertThat(testEmpreendimento.isIndCondominioFechado()).isEqualTo(UPDATED_IND_CONDOMINIO_FECHADO);
        assertThat(testEmpreendimento.getDistanciaCentroCidade()).isEqualTo(UPDATED_DISTANCIA_CENTRO_CIDADE);
        assertThat(testEmpreendimento.getCaminhoImagemEmpreendimento()).isEqualTo(UPDATED_CAMINHO_IMAGEM_EMPREENDIMENTO);

        // Validate the Empreendimento in ElasticSearch
        Empreendimento empreendimentoEs = empreendimentoSearchRepository.findOne(testEmpreendimento.getId());
        assertThat(empreendimentoEs).isEqualToComparingFieldByField(testEmpreendimento);
    }

    @Test
    @Transactional
    public void deleteEmpreendimento() throws Exception {
        // Initialize the database
        empreendimentoService.save(empreendimento);

        int databaseSizeBeforeDelete = empreendimentoRepository.findAll().size();

        // Get the empreendimento
        restEmpreendimentoMockMvc.perform(delete("/api/empreendimentos/{id}", empreendimento.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean empreendimentoExistsInEs = empreendimentoSearchRepository.exists(empreendimento.getId());
        assertThat(empreendimentoExistsInEs).isFalse();

        // Validate the database is empty
        List<Empreendimento> empreendimentos = empreendimentoRepository.findAll();
        assertThat(empreendimentos).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEmpreendimento() throws Exception {
        // Initialize the database
        empreendimentoService.save(empreendimento);

        // Search the empreendimento
        restEmpreendimentoMockMvc.perform(get("/api/_search/empreendimentos?query=id:" + empreendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empreendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].localizacao").value(hasItem(DEFAULT_LOCALIZACAO.toString())))
            .andExpect(jsonPath("$.[*].indCondominioFechado").value(hasItem(DEFAULT_IND_CONDOMINIO_FECHADO.booleanValue())))
            .andExpect(jsonPath("$.[*].distanciaCentroCidade").value(hasItem(DEFAULT_DISTANCIA_CENTRO_CIDADE.intValue())))
            .andExpect(jsonPath("$.[*].caminhoImagemEmpreendimento").value(hasItem(DEFAULT_CAMINHO_IMAGEM_EMPREENDIMENTO.toString())));
    }
}
