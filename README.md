<img width="869" height="750" alt="image" src="https://github.com/user-attachments/assets/ad5cc7f2-6a80-4fb6-82ac-a2128b030ae3" />

Created Reddit lookalike application with 4chan Anonymous status ( without User account registration ).

My vision of application is to have simple nested comment forum, like reddit, but without likes. 
User can create comment or post without registration ( but has to solve Captcha to confirm he is not robot ).
Nested comments work like on reddit, max level of nesting is 5, after that you have to click on "Continue thread" to see more nested replies. If there are more than 5 replies to comment, user has to click "Load more replies".
Using captcha we can have Anonymous status like on 4chan with prevention against spam, but have more readable comment section like reddit.

To start this application 

```bash
git clone https://github.com/radomirovicvladimir/reputeo
cd reputeo
docker-compose up --build -d
```

You can open project inside Intellij and in resources/requests, you can use predefined POST requests to test application.


