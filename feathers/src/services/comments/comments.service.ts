// Initializes the `comments` service on path `/comments`
import { Service } from '@feathersjs/feathers';
import { Application } from '../../declarations';
import createModel from '../../models/comments.model';
import { Comments } from './comments.class';
import hooks from './comments.hooks';
import Comment from './comments.interface';

// Add this service to the service type index
declare module '../../declarations' {
  interface ServiceTypes {
    'comments': Service<Comment> & Comments;
  }
}

export default function (app: Application): void {
  const options = {
    Model: createModel(app),
    paginate: app.get('paginate')
  };

  // Initialize our service with any options it requires
  app.use('/comments', new Comments(options, app));

  // Get our initialized service so that we can register hooks
  const service = app.service('comments');

  service.hooks(hooks);
}
