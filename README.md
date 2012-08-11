Simple

    Avatar("you@example.com").avatarUrl

To get a secure URL (for embedding on websites served over HTTPS/SSL)

    Avatar("you@example.com").ssl(true).avatarUrl

Setting all the properties

    Gravatar(email).ssl(true).default(Monster).maxRatedAs(R).forceDefault(true).size(100).avatarUrl

More info
[at Gravatar](gravatar.com/site/implement/images/)