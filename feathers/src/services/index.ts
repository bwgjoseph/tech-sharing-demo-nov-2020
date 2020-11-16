import debuggerService from 'feathers-debugger-service';
import { Application } from '../declarations';
import comments from './comments/comments.service';
import posts from './posts/posts.service';
import users from './users/users.service';
// Don't remove this comment. It's needed to format import lines nicely.

export default function (app: Application): void {
  app.configure(users);
  app.configure(posts);
  app.configure(comments);
  app.configure(debuggerService({ ui: true }));
}
