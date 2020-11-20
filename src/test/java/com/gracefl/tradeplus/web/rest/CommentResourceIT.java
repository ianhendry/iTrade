package com.gracefl.tradeplus.web.rest;

import com.gracefl.tradeplus.ITradeApp;
import com.gracefl.tradeplus.domain.Comment;
import com.gracefl.tradeplus.repository.CommentRepository;
import com.gracefl.tradeplus.service.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommentResource} REST controller.
 */
@SpringBootTest(classes = ITradeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommentResourceIT {

    private static final String DEFAULT_COMMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_BODY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMMENT_MEDIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT_MEDIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_MEDIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_MEDIA_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_ADDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ADDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_APPROVED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_APPROVED = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentMockMvc;

    private Comment comment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .commentTitle(DEFAULT_COMMENT_TITLE)
            .commentBody(DEFAULT_COMMENT_BODY)
            .commentMedia(DEFAULT_COMMENT_MEDIA)
            .commentMediaContentType(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(DEFAULT_DATE_ADDED)
            .dateApproved(DEFAULT_DATE_APPROVED);
        return comment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comment createUpdatedEntity(EntityManager em) {
        Comment comment = new Comment()
            .commentTitle(UPDATED_COMMENT_TITLE)
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED);
        return comment;
    }

    @BeforeEach
    public void initTest() {
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();
        // Create the Comment
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCommentTitle()).isEqualTo(DEFAULT_COMMENT_TITLE);
        assertThat(testComment.getCommentBody()).isEqualTo(DEFAULT_COMMENT_BODY);
        assertThat(testComment.getCommentMedia()).isEqualTo(DEFAULT_COMMENT_MEDIA);
        assertThat(testComment.getCommentMediaContentType()).isEqualTo(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testComment.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testComment.getDateApproved()).isEqualTo(DEFAULT_DATE_APPROVED);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCommentTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setCommentTitle(null);

        // Create the Comment, which fails.


        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentRepository.findAll().size();
        // set the field null
        comment.setDateAdded(null);

        // Create the Comment, which fails.


        restCommentMockMvc.perform(post("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentTitle").value(hasItem(DEFAULT_COMMENT_TITLE)))
            .andExpect(jsonPath("$.[*].commentBody").value(hasItem(DEFAULT_COMMENT_BODY)))
            .andExpect(jsonPath("$.[*].commentMediaContentType").value(hasItem(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_MEDIA))))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].dateApproved").value(hasItem(DEFAULT_DATE_APPROVED.toString())));
    }
    
    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.commentTitle").value(DEFAULT_COMMENT_TITLE))
            .andExpect(jsonPath("$.commentBody").value(DEFAULT_COMMENT_BODY))
            .andExpect(jsonPath("$.commentMediaContentType").value(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.commentMedia").value(Base64Utils.encodeToString(DEFAULT_COMMENT_MEDIA)))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.dateApproved").value(DEFAULT_DATE_APPROVED.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findById(comment.getId()).get();
        // Disconnect from session so that the updates on updatedComment are not directly saved in db
        em.detach(updatedComment);
        updatedComment
            .commentTitle(UPDATED_COMMENT_TITLE)
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(UPDATED_DATE_ADDED)
            .dateApproved(UPDATED_DATE_APPROVED);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedComment)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getCommentTitle()).isEqualTo(UPDATED_COMMENT_TITLE);
        assertThat(testComment.getCommentBody()).isEqualTo(UPDATED_COMMENT_BODY);
        assertThat(testComment.getCommentMedia()).isEqualTo(UPDATED_COMMENT_MEDIA);
        assertThat(testComment.getCommentMediaContentType()).isEqualTo(UPDATED_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testComment.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testComment.getDateApproved()).isEqualTo(UPDATED_DATE_APPROVED);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Delete the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
