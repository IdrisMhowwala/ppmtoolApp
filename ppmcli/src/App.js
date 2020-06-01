import React from 'react';
import logo from './logo.svg';
import './App.css';
import Dashboard from './components/Dashboard';
import Header from './components/layout/Header';
import 'bootstrap/dist/css/bootstrap.min.css'
import {BrowserRouter as Router, Route} from 'react-router-dom';
import AddProject from './components/projects/AddProject';
import UpdateProject from './components/projects/UpdateProject';
import {Provider} from 'react-redux';
import store from './store';
import ProjectBoard from './components/ProjectBoard/ProjectBoard';
import AddProjectTask from './components/ProjectBoard/ProjectTasks/AddProjectTask';

function App() {
  return (
    <Provider store={store}>
    <Router>
    <div className="container">
      <Header/>
    </div>
    <Route exact path="/dashboard" component={Dashboard}></Route>
    <Route exact path="/addProject" component={AddProject}></Route>
    <Route exact path="/updateProject/:id" component={UpdateProject}></Route>
    <Route exact path="/projectBoard/:id" component={ProjectBoard}></Route>
    <Route exact path="/addProjectTask/:id" component={AddProjectTask}></Route>
    </Router>
    </Provider>
  );
}

export default App;
