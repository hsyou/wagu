<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">

          <div class="modal-header">
            <slot name="header">
              <img src="@/assets/default-user.png"/>
              <span>{{tweet.userName}}</span>
               <a class="modal-close-button" @click="$emit('close')">
                X
              </a>
            </slot>
          </div>

          <div class="modal-body">
            <slot name="body">
              <div class="tweet-content">
                {{tweet.content}}
              </div>
              <div class="comment-write-box" >
                <div class="writable" :class="{extend: extendFlag}" @keyup="updateText($event)" contenteditable="true" @focus="extendBox" @blur="focusOut">
                        {{userText}}
                  <span v-show="!extendFlag">What's on your mind?</span>
                </div>
                <div class="user-img">
                  <img src="@/assets/default-user.png"/>
                </div>

                <div v-show="extendFlag" class="button-list">
                  <button type="button" @click="tweet">Reply</button>
                </div>
              </div>
              <div class="comment-list">
                <ul>
                  <li v-for="comment in commentList" :key="comment.commentID" class="comment-li">
                    <img src="@/assets/default-user.png"/>
                    <span>{{tweet.userName}}</span>
                    <div class="comment-text">
                      <span>{{comment.comment}}</span>
                    </div>
                  </li>
                </ul>
              </div>
            </slot>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: 'timeline-detail',
  props: ['tweet'],
  data () {
    return {
      extendFlag: false,
      userText: '',
      commentList: [
        {
          userID: 1,
          commentID: 1,
          userName: 'Kevin',
          comment: `ya lol, I couldn't push myself to listen to BTS, i really didn't like kpop, but i like tapping games so when i heard of superstar bts i played it and after listening and playing for a week, i found myself enjoying their music and now here i am selling my life for them`,
          postDate: '2018-09-02 12:00'
        },
        {
          userID: 1,
          commentID: 2,
          userName: 'Kevin',
          comment: 'Hi',
          postDate: '2018-09-02 12:00'
        },
        {
          userID: 1,
          commentID: 3,
          userName: 'Kevin',
          comment: 'Hi',
          postDate: '2018-09-02 12:00'
        },
        {
          userID: 1,
          commentID: 4,
          userName: 'Kevin',
          comment: 'Hi',
          postDate: '2018-09-02 12:00'
        }
      ]
    }
  },
  methods: {
    extendBox () {
      this.extendFlag = true
    },
    focusOut () {
      if (this.userText === '') {
        this.extendFlag = false
        this.$forceUpdate()
      }
    },
    updateText (event) {
      this.userText = event.target.innerText
    }
  }
}
</script>

<style scoped>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: inline-block;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: inline-block;
  position: relative;
  top:150px;
}

.modal-container {
  width: 640px;
  text-align: center;
  margin: 0px auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}

.modal-header {
  text-align: left;
  padding: 20px 30px;
}

.modal-header img { width:28px} 
.modal-header span { padding-left:20px;position:relative;top:-7px;font-weight: bold} 

.modal-body {
}

.modal-default-button {
  float: right;

}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
  opacity: 0;
}

.modal-leave-active {
  opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modal-close-button {
  background: #fff;
  border:none;
  color:#a1a1a1;
  float:right;
  cursor: pointer;
}
.tweet-content{
  padding: 0 30px 10px 30px;
  text-align: left;
}

 .comment-write-box {
    background: #a1a1a1;
    color:#1DA1F2;
    background: #E8F5FD;
    padding: 10px 0 10px 0;
  }
  .comment-write-box .writable {
    width:510px;
    margin-right:10px;
    float: right;
    position: relative;
    right:20px;
    border:1px solid #1DA1F2;
    border-radius:5px;
    padding:5px 10px 5px 10px;
    background: #fff;
    color: #a1a1a1;
    text-align: left;
  }
  .comment-write-box .user-img > img{
    width:28px;
    position: relative;
    left:-20px;
  }

  .comment-write-box .writable:focus {
    outline: none;
  }
  .comment-write-box .writable.extend {
    height: 80px;
    color:#000;
  }
  .comment-write-box .button-list {
    padding: 10px 0 10px 0;
    display: inline-block;
    width: 600px;
    text-align: right;
  }
  .comment-list .comment-li {
    text-align: left;
  }
  .comment-list .comment-li > span{
    font-weight: bold;
    padding-left:20px;
    position: relative;
    top:-7px;
  }
  .comment-list .comment-li > img {
    width:28px;
  }
  .comment-list .comment-li .comment-text {
    padding:10px 0
  }
</style>
