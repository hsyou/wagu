<template>
  <div class="timeline-center">
    <timeline-detail
      v-if="detailFlag"
      @close="detailFlag = false"
      :tweet="selectedTweet"
    ></timeline-detail>
    <div class="timeline-write-box">
      
      <div class="writable" :class="{extend: extendFlag}" @keyup="updateText($event)" contenteditable="true" @focus="extendBox" @blur="focusOut">
        {{userText}}
        <span v-show="!extendFlag">What's on your mind?</span>
      </div>
      <div class="user-img">
        <img src="@/assets/default-user.png"/>
      </div>

      <div v-show="extendFlag" class="button-list">
        <button type="button" @click="tweet">Tweet</button>
      </div>
    </div>
    <div class="timeline-news-box">
      <ul>
        <li v-for="tweet in tweetList" :key="tweet.tweetID" @click="showDetail(tweet)">
          <div class="tweet-left">
            <img src="@/assets/default-user.png"/>
          </div>
          <div class="tweet-right">
            <span class="username">
              {{tweet.userName}}
            </span>
            <span class="date">
              {{tweet.createDate}}
            </span>
            <div class="tweet-content">
              {{tweet.content}}
            </div>
            <div class="tweet-button-list">
              <div class="reply-btn">
                <span>{{tweet.replyCount}}</span>
              </div>
              <div class="favorite-btn">
                <span>{{tweet.like}}</span>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import TimelineDetail from './TimelineDetail'

export default {
  name: 'timeline-center',
  components: {
    TimelineDetail
  },
  data () {
    return {
      selectedTweet: {},
      detailFlag: false,
      extendFlag: false,
      userText: '',
      tweetList: [
        {
          tweetID: 1,
          userID: 1,
          userName: '문재인',
          content: `데이터 경제 활성화를 위한 규제혁신 현장을 찾았습니다. 우리의 목표는 분명합니다. 데이터의 개방과 공유를 확대하고 신기술, 신산업, 새로운 제품과 서비스를 창출하는 것입니다. 개인정보 보호의 원칙을 분명하게 지키면서
안전한 데이터를 활용하게 하는 것입니다`,
          createDate: '2018-09-01 18:10',
          like: 302,
          replyCount: 3
        },
        {
          tweetID: 2,
          userID: 1,
          userName: '문재인',
          createDate: '2018-09-01 18:10',
          content: '국민이 가장 신뢰하는 기관인 헌법재판소의 창립 서른 돌을 진심으로 축하합니다. 헌법재판소가 국민주권을 강화하고 성숙한 민주공화국으로 가는 길에서 국민의 가장 든든한 동반자가 되어주실 것으로 믿으며, 무궁한 발전을 기원합니다.',
          like: 302,
          replyCount: 3
        },
        {
          tweetID: 3,
          userID: 2,
          userName: '장기하',
          createDate: '2018-09-01 18:10',
          content: `저희 곧 5집 내는데 공연 먼저 시작해요. 9월부터 11월까지. 오시는 분들께는 헤드폰을 씌워드릴 거예요. 스피커는 안 쓰고요. 5집 곡들도 조금씩 미리 들려드릴게요. 공연도 앨범도 제목은 mono. 오세요.`,
          like: 302,
          replyCount: 3
        },
        {
          tweetID: 4,
          userID: 1,
          userName: '이적 Juck Lee',
          createDate: '2018-09-01 18:10',
          content: `노덕래 군 마지막 가는 길에 비가 오네요. 최고의 베이시스트이자 참 착한 사람 덕래. 좋은 곳에서 평안하길 기원합니다. 덕래가 전곡 베이스를 연주한 <사랑> 앨범 듣고 공연할 때마다 생각이 나는 걸 어쩔 수 없을 거예요. 남은 가족들 부디 강건하시길. 삼가 고인의 명복을 빕니다. 덕래야 안녕..`,
          like: 302,
          replyCount: 3
        },
        {
          tweetID: 5,
          userID: 3,
          userName: 'MYSTIC Entertainment',
          createDate: '2018-09-01 18:10',
          content: `[Album Cover] 정진운 - Koong! Pop!
          -Track-
          1. 머리만 아파
          2. All I Need Is You -title✔️
          3. Shine
          🎸2018. 09. 02 PM 6:00 음원 공개!
          (*M/V는 3일에 공개됩니다.)`,
          like: 302,
          replyCount: 3
        }
      ]
    }
  },
  methods: {
    showDetail (tweet) {
      this.selectedTweet = tweet
      this.detailFlag = true
    },
    tweet () {

    },
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
  .timeline-center {
    width:588px;
    display: inline-block;
  }
  .timeline-write-box {
    background: #a1a1a1;
    color:#1DA1F2;
    background: #E8F5FD;
    padding: 10px 0 10px 0;
  }
  .timeline-write-box .writable {
    width:460px;
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
  .timeline-write-box .user-img > img{
    width:28px;
  }

  .timeline-write-box .writable:focus {
    outline: none;
  }

  .timeline-write-box .writable.extend {
    height: 80px;
    color:#000;
  }

  .timeline-write-box .button-list {
    padding:10px 0 10px 0;
    display: inline-block;
    width:548px;
    text-align: right;

  }

  .timeline-news-box{background: #fff}
  .tweet-left{width:60px;display: inline-block; 
    vertical-align:top;}
  .tweet-left > img { width:28px;}  
  .tweet-right{text-align: left; width:480px;display: inline-block;}
  .tweet-right .tweet-content{padding-top:10px}
  .tweet-right .username {font-weight:bold}
  .tweet-right .date {padding-left:20px;font-size:14px;color:#a1a1a1;}
  .tweet-right .tweet-button-list {padding-top:20px}
  .tweet-right .tweet-button-list > div{display: inline-block;padding-right:25px;width:60px;}
  .tweet-right .tweet-button-list > div > span{position:relative;left:25px;font-size:14px;}
  .tweet-right .tweet-button-list .reply-btn {background: url(../../assets/reply.png) no-repeat left center;}
  .tweet-right .tweet-button-list .favorite-btn {background: url(../../assets/favorite_blank.png) no-repeat left center;}
  </style>
