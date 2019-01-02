package com.hsyou.wagu;

import com.hsyou.wagu.model.Account;
import com.hsyou.wagu.model.Comment;
import com.hsyou.wagu.model.LikePost;
import com.hsyou.wagu.model.Post;
import com.hsyou.wagu.repository.AccountRepository;
import com.hsyou.wagu.repository.CommentRepository;
import com.hsyou.wagu.repository.LikePostRepository;
import com.hsyou.wagu.repository.PostRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
/*
By default DataJpaTest replaces your DataSource with an embedded database but you don't have one.
So, if you want to test with MySQL, replace your test as follows:

@AutoConfigureTestDatabase(replace = NONE)

If you want to use an in-memory database for those tests, you need to add one to the test classpath. Add this to your gradle file
testCompile('com.h2database:h2')

 */
public class PostTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LikePostRepository likePostRepository;

    @After
    public void cleanup() {
        /**
         이후 테스트 코드에 영향을 끼치지 않기 위해
         테스트 메소드가 끝날때 마다 respository 전체 비우는 코드
         **/
//        postRepository.deleteAll();
    }

    @Test
    @Rollback(false)
    public void 포스트_등록하기(){

        /**
         * @DataJpaTest 에 보면 @Transactional 어노테이션으로 트랜잭션을 걸어놨기 때문에 아래 코드는
         * 실제 데이터베이스에 싱크되지 않고 롤백이 된다.
         *
         * hibernate는 필요할때만 쿼리를 실제 데이터베이스에 싱크하기 때문에
         * 아래 코드가 롤백될 것을 알고 쿼리를 실행하지 않음
         *
         * 이를 해결하려면 @Rollback(flase) 어노테이션을 테스트 메서드에 추가한다.
         */

        //Given
        Account account = new Account();
        account.setName("test");
        account.setEmail("tester@gmail.com");

        Account rstAcnt = accountRepository.save(account);

        //When
        //(Post post, long accountId)

        Post requestPost = new Post();
        requestPost.setTitle("post1");
        requestPost.setContents("Hello world");
        long accountId = rstAcnt.getId();

        Optional<Account> optAccount = accountRepository.findById(accountId);

        Post post = Post.createPost(requestPost, optAccount.get());

        Post resultPost = postRepository.save(post);

        //Then
        assertThat(resultPost.getId()).isNotNull();


        //When
        List<Post> posts = postRepository.findAll();

        //Then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(resultPost);

        //When
        Page<Post> page = postRepository.findAll(PageRequest.of(0,10));

        //Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);

        //When
        page = postRepository.findByTitleContains(requestPost.getTitle(), PageRequest.of(0, 10));
        //Then
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getNumberOfElements()).isEqualTo(1);


    }

    @Test
    @Rollback(false)
    public void 포스트_댓글_등록하기() {
        //Given
        Account account = new Account();
        account.setName("test");
        account.setEmail("tester@gmail.com");

        Account rstAcnt = accountRepository.save(account);

        Post post = new Post();
        post.setTitle("post1");
        post.setContents("hello world");

        Post rstPost = postRepository.save(Post.createPost(post, rstAcnt));
        //When
        //(Comment comment, long postId, long accountId)
        Comment rqstComment =new Comment();
        rqstComment.setContents("hi~!");

        long accountId = rstAcnt.getId();
        long postId = rstPost.getId();

        Optional<Account> optAccount = accountRepository.findById(accountId);
        Optional<Post> optPost = postRepository.findById(postId);

        Comment comment = Comment.createComment(rqstComment, optAccount.get(), optPost.get());

        Comment resultComment = commentRepository.save(comment);

        //Then
        Optional<Comment> byId = commentRepository.findById(resultComment.getId());
        assertThat(byId.isPresent()).isEqualTo(true);
        assertThat(byId.get().getPost().getId()).isEqualTo(postId);
        assertThat(byId.get().getWriter().getId()).isEqualTo(accountId);

        System.out.println(byId.get());

        Optional<Post> thenPost = postRepository.findById(postId);
        assertThat(thenPost.get().getComments().contains(byId.get())).isEqualTo(true);
        assertThat(thenPost.get().getComments().size()).isEqualTo(thenPost.get().getCommentCount());

        Optional<Account> thenAcc = accountRepository.findById(accountId);
        assertThat(thenAcc.get().getComments().contains(byId.get())).isEqualTo(true);
    }

    @Test
    @Rollback(false)
    public void 포스트_좋아요_등록하기(){
        //Given
        Account account = new Account();
        account.setName("test");
        account.setEmail("tester@gmail.com");

        Account rstAcnt = accountRepository.save(account);

        Post post = new Post();
        post.setTitle("post1");
        post.setContents("hello world");

        Post rstPost = postRepository.save(Post.createPost(post, rstAcnt));
        //When
        //(long postId, long accountId)
        long postId = rstPost.getId();
        long accountId = rstAcnt.getId();
        Optional<Account> optAccount = accountRepository.findById(accountId);
        Optional<Post> optPost = postRepository.findById(postId);

        LikePost likePost = LikePost.createLikePost(optPost.get(), optAccount.get());
        LikePost rstLikePost = likePostRepository.save(likePost);

        //Then
        Optional<LikePost> byId = likePostRepository.findById(rstLikePost.getId());
        assertThat(byId.get().getAccount().getId()).isEqualTo(accountId);
        assertThat(byId.get().getPost().getId()).isEqualTo(postId);

        Optional<Post> thenPost = postRepository.findById(postId);

        assertThat(thenPost.get().getLikePosts().size()).isEqualTo(thenPost.get().getLikeCount());

        Optional<Account> thenAcc = accountRepository.findById(accountId);
        assertThat(thenAcc.get().getLikePosts().contains(byId.get())).isEqualTo(true);

    }
}
