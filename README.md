<img width="869" height="750" alt="image" src="https://github.com/user-attachments/assets/ad5cc7f2-6a80-4fb6-82ac-a2128b030ae3" />

Created Reddit lookalike application with 4chan Anonymous status ( without User account registration ).

What was on my mind? 🧠

4chan is good anonymous forum, where you can have discussion about any topic while being completely anonymous. BUT, 4chan has awful comment UX. That is where Reddit nested comments step up.
I implemented nested comments to have max nesting of 5 level, after which you must continue with a thread similar to reddit. Every time there is more than 5 comments in horizontal or vertical, you must load more comments, so I can protect of performance drop.
What I overlooked is that anonymous user can not edit/delete their comments, because at that moment I was more fixated at register/login Account based application, where you can have non unique Display name.

Implemented captcha and rate-limiter to solve problem of scripting/bot accounts ruining experience of everyone.

This application is in pure development stage, so it can not be used in production.

To start this application 

```bash
git clone https://github.com/radomirovicvladimir/reputeo
cd reputeo
docker-compose up --build -d
```

You can open project inside Intellij and in resources/requests, you can use predefined POST requests to test application.


